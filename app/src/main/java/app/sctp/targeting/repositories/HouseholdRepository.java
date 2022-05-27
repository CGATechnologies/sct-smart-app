package app.sctp.targeting.repositories;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.room.Transaction;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import app.sctp.R;
import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.IndividualDao;
import app.sctp.targeting.dao.TargetedHouseholdDao;
import app.sctp.targeting.models.HouseholdSelectionResults;
import app.sctp.targeting.models.SelectionCount;
import app.sctp.targeting.models.SelectionStatus;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.targeting.models.TargetedHouseholdUpdateRequest;
import app.sctp.targeting.models.TargetingSession;
import app.sctp.targeting.services.TargetingService;
import app.sctp.utils.PlatformUtils;
import app.sctp.utils.UiUtils;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class HouseholdRepository extends BaseRepository {
    private final Handler handler;
    private final IndividualDao individualDao;
    private final TargetedHouseholdDao householdDao;

    public HouseholdRepository(@NonNull SctpAppDatabase database) {
        super(database);
        this.handler = new Handler(Looper.getMainLooper());
        this.householdDao = database.targetedHouseholdDao();
        this.individualDao = database.individualDao();
    }

    @Transaction
    public void save(List<TargetedHousehold> households) {
        super.post(() -> householdDao.insert(households));
    }

    @Transaction
    public void save(TargetedHousehold household) {
        super.post(() -> householdDao.insert(household));
    }

    public DataSource.Factory<Integer, TargetedHousehold> getSessionHouseholds(Long sessionId) {
        return householdDao.getBySessionId(sessionId);
    }

    public int getHouseholdCount(Long sessionId) {
        return householdDao.countSessionHouseholds(sessionId);
    }

    public List<HouseholdSelectionResults> getHouseholdSelectionResultsForSession(Long sessionId, int offset, int count) {
        return householdDao.getHouseholdSelectionResultsForSession(sessionId, offset, count);
    }

    public SelectionCount getHouseholdSelectionCount(Long sessionId) {
        return householdDao.sessionHouseholdsSelected(sessionId);
    }

    public void updateHouseholdStatus(Long sessionId, SelectionStatus selectionStatus) {
        post(() -> householdDao.updateHouseholdStatus(sessionId, selectionStatus));
    }

    public boolean sessionHouseholdsSelected(Long sessionId) {
        return getHouseholdSelectionCount(sessionId).getUnselected() == 0;
    }

    public void synchronizeHouseholds(Long sessionId, TargetingService service, TargetingSession.MeetingPhase phase, SyncListener listener) {
        handler.post(() -> {
            listener.onStart();
            listener.onMessage("Preparing to send data...");
        });
        post(() -> {
            try {
                int count = getHouseholdCount(sessionId);
                int pageSize = 100;
                int trips = (int) ((count / pageSize) + ((count % pageSize) > 0 ? 1 : 0));

                List<HouseholdSelectionResults> results;

                handler.post(() -> listener.onInitializeProgress(trips));

                for (int i = 0, offset = 0; i < trips; i++, offset += pageSize) {
                    final int progress = i;
                    handler.post(() -> listener.onProgress(progress));

                    // TODO only get households that have been modified at each stage
                    results = getHouseholdSelectionResultsForSession(sessionId, offset, pageSize);

                    final int records = results.size();
                    final String message = String.format(Locale.US, "sending %,d of %,d household%s.",
                            records, count, records != 1 ? "s" : "");
                    handler.post(() -> listener.onMessage(message));

                    Call<Void> call;
                    Response<Void> response;
                    TargetedHouseholdUpdateRequest request = new TargetedHouseholdUpdateRequest(results);

                    switch (phase) {
                        case second_community_meeting:
                            call = service.updateSecondCommunityMeetingHouseholds(sessionId, request);
                            break;
                        case district_meeting:
                            call = service.updateDistrictMeetingHouseholds(sessionId, request);
                            break;
                        default:
                            throw new UnsupportedOperationException("operation not unsupported for phase: " + phase);
                    }

                    response = call.execute();
                    if (!response.isSuccessful()) {
                        throw new HttpException(response);
                    }
                }
                listener.onComplete();
            } catch (Exception exception) {
                PlatformUtils.printStackTrace(exception);
                listener.onError(exception);
            }
        });
    }

    public void update(TargetedHousehold household) {
        post(() -> householdDao.update(household));
    }

    public interface SyncListener {
        void onMessage(String message);

        void onComplete();

        void onStart();

        void onError(Exception exception);

        void onInitializeProgress(int max);

        void onProgress(int progress);
    }

}
/*

try {


        response = getApplicationConfiguration()
        .getService(TargetingService.class)
        .uploadHouseholdSelectionResults(session.getId(), request).execute();
        if (response.isSuccessful()) {
        success.set(true);
        // TODO Set session as Closed ?
        } else {
        throw new HttpException(response);
        }
        } catch (Exception e) {
        PlatformUtils.printStackTrace(e);
        } finally {
        runOnUiThread(() -> {
        progressDialog.dismiss();
        if (success.get()) {
        UiUtils.snackbar(binding.getRoot(), R.string.household_updates_sent);
        } else {
        UiUtils.snackbar(binding.getRoot(), R.string.household_updates_failed, true);
        }
        });
        }*/

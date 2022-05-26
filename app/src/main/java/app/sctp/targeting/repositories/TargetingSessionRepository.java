package app.sctp.targeting.repositories;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.TargetedClusterDao;
import app.sctp.targeting.dao.TargetingSessionDao;
import app.sctp.targeting.models.GetTargetingSessionsResponse;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.TargetedCluster;
import app.sctp.targeting.models.TargetingSession;
import app.sctp.targeting.services.TargetingService;
import app.sctp.utils.PlatformUtils;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class TargetingSessionRepository extends BaseRepository {
    private static final int DOWNLOAD_PAGE_SIZE = 100;

    private final Handler handler;
    private final TargetingSessionDao dao;
    private final TargetedClusterDao clusterDao;

    public TargetingSessionRepository(@NonNull SctpAppDatabase database) {
        super(database);
        dao = database.targetingSessionDao();
        clusterDao = database.targetedClusterDao();
        handler = new Handler(Looper.getMainLooper());
    }

    public void saveAll(List<TargetingSession> sessions) {
        post(() -> dao.insert(sessions));
    }

    public DataSource.Factory<Integer, TargetingSession> getSecondCommunityMeetingSessions(LocationSelection location) {
        return dao.getSecondCommunityMeetingSessions(location);
    }

    public DataSource.Factory<Integer, TargetingSession> getDistrictMeetingSessions(LocationSelection location) {
        return dao.getDistrictMeetingSessions(location);
    }

    /**
     * Download targeting sessions from remote
     *
     * @param location
     * @param phase
     * @param service
     * @param listener
     */
    public void downloadTargetingSessions(LocationSelection location, TargetingSession.MeetingPhase phase, TargetingService service, SessionDownloadListener listener) {
        post(() -> {
            try {
                AtomicInteger page = new AtomicInteger(0);
                AtomicInteger pageCount = new AtomicInteger(0);

                Call<GetTargetingSessionsResponse> call;

                switch (phase) {
                    case second_community_meeting:
                        call = service.getSecondCommunityMeetingSessions(
                                LocationSelection.codeOrZero(location.getTraditionalAuthority())
                                , LocationSelection.codeOrZero(location.getCluster())
                                , LocationSelection.codeOrZero(location.getZone())
                                , LocationSelection.codeOrZero(location.getVillage())
                                , page.get()
                                , DOWNLOAD_PAGE_SIZE
                        );
                        break;
                    case district_meeting:
                        call = service.getSecondDistrictMeetingSessions(
                                LocationSelection.codeOrZero(location.getTraditionalAuthority())
                                , LocationSelection.codeOrZero(location.getCluster())
                                , LocationSelection.codeOrZero(location.getZone())
                                , LocationSelection.codeOrZero(location.getVillage())
                                , page.get()
                                , DOWNLOAD_PAGE_SIZE
                        );
                        break;
                    default:
                        throw new IllegalArgumentException("unsupported meeting phase value " + phase);
                }

                handler.post(listener::onStart);

                do {
                    handler.post(() -> listener.onMessage("Downloading sessions..."));

                    Response<GetTargetingSessionsResponse> sessionResponse = call.execute();

                    if (sessionResponse.isSuccessful()) {
                        GetTargetingSessionsResponse response = sessionResponse.body();

                        pageCount.compareAndSet(0, response.getTotalPages());
                        handler.post(() -> listener.onProgressTotalAvailable(pageCount.get()));

                        if (!response.getItems().isEmpty()) {
                            dao.saveAll(response.getItems());

                            for (TargetingSession session : response.getItems()) {
                                if (!session.getClusters().isEmpty()) {
                                    clusterDao.saveAll(TargetedCluster.of(session.getClusters(), session.getId()));
                                }
                            }
                        }

                        // download households under this session
                        downloadSessionHouseholds(response.getItems(), service, listener);

                        handler.post(() -> listener.onProgress(page.get(), pageCount.get()));
                    } else {
                        throw new HttpException(sessionResponse);
                    }
                } while (page.incrementAndGet() < pageCount.get());
                handler.post(listener::onCompleted);
            } catch (Exception exception) {
                PlatformUtils.printStackTrace(exception);
                handler.post(() -> {
                    listener.onError(exception);
                });
            }
        });
    }

    private void downloadSessionHouseholds(List<TargetingSession> sessions, TargetingService service, SessionDownloadListener listener) throws IOException {
        /*handler.post(() -> listener.onMessage("Downloading household data..."));
        for (TargetingSession session : sessions) {
            AtomicInteger page = new AtomicInteger(0);
            AtomicInteger pageCount = new AtomicInteger(0);
            do {
                Response<HouseholdDetailResponse> response =
                        service
                                .getHouseholdsFromPreEligibilitySession(session.getId(), page.get())
                                .execute();
                if (response.isSuccessful()) {
                    HouseholdDetailResponse detailResponse = response.body();
                    pageCount.compareAndSet(0, detailResponse.getTotalPages());

                    if (!detailResponse.getItems().isEmpty()) {
                        for (HouseholdDetails householdDetails : detailResponse.getItems()) {
                            // TODO default to eligible
                            householdDetails.getHousehold().setSelection(SelectionStatus.Eligible);
                            householdViewModel.save(householdDetails.getHousehold());
                            if (!householdDetails.getMemberDetails().isEmpty()) {
                                individualViewModel.save(householdDetails.getMemberDetails());
                            }
                        }
                    }
                } else {
                    throw new HttpException(response);
                }
            } while (page.incrementAndGet() < pageCount.get());
        }*/
    }

    public interface SessionDownloadListener {
        void onStart();

        void onCompleted();

        void onProgressTotalAvailable(int total);

        void onError(Exception e);

        void onMessage(String message);

        void onProgress(int progress, int total);
    }
}

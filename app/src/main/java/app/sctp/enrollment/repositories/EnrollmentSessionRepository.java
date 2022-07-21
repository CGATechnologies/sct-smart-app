package app.sctp.enrollment.repositories;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import app.sctp.enrollment.dao.EnrollmentHouseholdDao;
import app.sctp.enrollment.dao.EnrollmentSessionDao;
import app.sctp.enrollment.models.EnrollmentSession;
import app.sctp.enrollment.models.GetEnrollmentSessionsResponse;
import app.sctp.enrollment.services.EnrollmentService;
import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.IndividualDao;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.utils.DownloadOptionsDialog;
import app.sctp.utils.PlatformUtils;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class EnrollmentSessionRepository extends BaseRepository {
    private static final int DOWNLOAD_PAGE_SIZE = 100;

    private final Handler handler;
    private final EnrollmentSessionDao dao;
    private final IndividualDao individualDao;
//    private final TargetedClusterDao clusterDao;
    private final EnrollmentHouseholdDao householdDao;

    public EnrollmentSessionRepository(@NonNull SctpAppDatabase database) {
        super(database);
        dao = database.enrollmentSessionDao();
        individualDao = database.individualDao();
//        clusterDao = database.targetedClusterDao();
        householdDao = database.enrollmentHouseholdDao();
        handler = new Handler(Looper.getMainLooper());
    }

    public void saveAll(List<EnrollmentSession> sessions) {
        post(() -> dao.insert(sessions));
    }

    /**
     * Download targeting sessions from remote
     *
     * @param location
     * @param service
     * @param listener
     */
    public void downloadEnrollmentSessions(
            LocationSelection location,
            EnrollmentService service,
            EnrollmentSessionRepository.SessionDownloadListener listener,
            DownloadOptionsDialog.DownloadOption downloadOption) {
        post(() -> {
            try {
                AtomicInteger page = new AtomicInteger(0);
                AtomicInteger pageCount = new AtomicInteger(0);

                Call<GetEnrollmentSessionsResponse> call;


                        call = service.getSecondCommunityMeetingSessions(
                                LocationSelection.codeOrZero(location.getTraditionalAuthority())
                                , LocationSelection.codeOrZero(location.getCluster())
                                , LocationSelection.codeOrZero(location.getZone())
                                , LocationSelection.codeOrZero(location.getVillage())
                                , page.get()
                                , DOWNLOAD_PAGE_SIZE
                        );


                handler.post(listener::onStart);

                do {
                    handler.post(() -> listener.onMessage("Downloading sessions..."));

                    Response<GetEnrollmentSessionsResponse> sessionResponse = call.execute();

                    if (sessionResponse.isSuccessful()) {
                        GetEnrollmentSessionsResponse response = sessionResponse.body();

                        pageCount.compareAndSet(0, response.getTotalPages());
                        handler.post(() -> listener.onProgressTotalAvailable(pageCount.get()));

                        if (!response.getItems().isEmpty()) {
                            dao.saveAll(response.getItems(), downloadOption);

                            for (EnrollmentSession session : response.getItems()) {
                                /*if (!session.getClusters().isEmpty()) {
                                    clusterDao.saveAll(TargetedCluster.of(session.getClusters(), session.getId()), downloadOption);
                                }*/
                            }
                        }

                        // download households under this session
                        downloadSessionHouseholds(response.getItems(), service, listener, downloadOption);

                        handler.post(() -> listener.onProgress(page.get(), pageCount.get()));
                    } else {
                        throw new HttpException(sessionResponse);
                    }
                } while (page.incrementAndGet() < pageCount.get());
                handler.post(listener::onCompleted);
            } catch (Exception exception) {
                PlatformUtils.printStackTrace(exception);
                handler.post(() -> listener.onError(exception));
            }
        });
    }

    private void downloadSessionHouseholds(
            List<EnrollmentSession> sessions,
            EnrollmentService service,
            EnrollmentSessionRepository.SessionDownloadListener listener,
            DownloadOptionsDialog.DownloadOption downloadOption) throws IOException {
        handler.post(() -> listener.onMessage("Downloading household data..."));
        for (EnrollmentSession session : sessions) {
            AtomicInteger page = new AtomicInteger(0);
            AtomicInteger pageCount = new AtomicInteger(0);
            do {
                Call<GetEnrollmentSessionsResponse> call;

//                call = service.getSecondCommunityMeetingTargetingSessionHouseholds(session.getId(), page.get());


                Response<TargetedHouseholdsResponse> response = call.execute();
                if (response.isSuccessful()) {
                    TargetedHouseholdsResponse detailResponse = response.body();
                    pageCount.compareAndSet(0, detailResponse.getTotalPages());

                    if (!detailResponse.getItems().isEmpty()) {
                        for (TargetedHousehold household : detailResponse.getItems()) {
                            householdDao.insert(household, downloadOption);
                            if (!household.getMemberDetails().isEmpty()) {
                                individualDao.saveAll(household.getMemberDetails(), downloadOption);
                            }
                        }
                    }
                } else {
                    throw new HttpException(response);
                }
            } while (page.incrementAndGet() < pageCount.get());
        }
    }

    public void update(TargetingSession session) {
        post(() -> dao.update(session));
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


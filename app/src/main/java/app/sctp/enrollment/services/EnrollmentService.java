package app.sctp.enrollment.services;

import app.sctp.enrollment.models.GetEnrollmentSessionsResponse;
import app.sctp.targeting.models.GetTargetingSessionsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class EnrollmentService {

    @GET("/targeting/meetings/second-community-meeting")
    Call<GetEnrollmentSessionsResponse> getEnrollmentSessions(
            @Query("traditional-authority-code") Long taCode,
            @Query("village-cluster-code") Long villageCluster,
            @Query("zone-code") Long zone,
            @Query("village-code") Long village,
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );
}

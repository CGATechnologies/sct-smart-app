package app.sctp.enrollment.services;

import app.sctp.enrollment.models.EnrollmentHouseholdsResponse;
import app.sctp.enrollment.models.GetEnrollmentSessionsResponse;
import app.sctp.targeting.models.TargetedHouseholdsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EnrollmentService {

    @GET("/enrollment/sessions")
    Call<GetEnrollmentSessionsResponse> getEnrollmentSessions(
            @Query("traditional-authority-code") Long taCode,
            @Query("village-cluster-code") Long villageCluster,
            @Query("zone-code") Long zone,
            @Query("village-code") Long village,
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    @GET("/enrollment/sessions/households")
    Call<EnrollmentHouseholdsResponse> getEnrollmentSessionHouseholds(
            @Query("enrollment-session-id") Long sessionId,
            @Query("page") int page
    );
}

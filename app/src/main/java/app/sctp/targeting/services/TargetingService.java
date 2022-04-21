package app.sctp.targeting.services;


import app.sctp.core.net.api.ext.ApiCall;
import app.sctp.targeting.models.HouseholdDetailResponse;
import app.sctp.targeting.models.PreEligibilityVerificationSessionResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TargetingService {
    /*@GET("/targeting/pre-eligibility/sessions")
    ApiCall<PreEligibilityVerificationSessionResponse> getPreEligibilitySessions(
            @Query("traditional-authority-code") Long taCode,
            @Query("village-cluster-code") Long villageCluster,
            @Query("zone-code") Long zone,
            @Query("village-code") Long village,
            @Query("page") int page
    );*/

    // TODO Run in one single background thread instead of recursive
    @GET("/targeting/pre-eligibility/sessions")
    Call<PreEligibilityVerificationSessionResponse> getPreEligibilitySessions(
            @Query("traditional-authority-code") Long taCode,
            @Query("village-cluster-code") Long villageCluster,
            @Query("zone-code") Long zone,
            @Query("village-code") Long village,
            @Query("page") int page
    );

    @GET("/targeting/pre-eligibility/sessions/{sessionId}/households")
    Call<HouseholdDetailResponse> getHouseholdsFromPreEligibilitySession(@Path("sessionId") Long sessionId, @Query("page") int page);

    /*
    @GET("/targeting/pre-eligibility/sessions/{sessionId}/households")
    ApiCall<HouseholdDetailResponse> getHouseholdsFromPreEligibilitySession(@Path("sessionId") Long sessionId, @Query("page") int page);*/
}

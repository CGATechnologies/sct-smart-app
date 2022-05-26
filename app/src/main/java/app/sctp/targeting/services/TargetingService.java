package app.sctp.targeting.services;


import app.sctp.targeting.models.CommunityMeetingSessionResponse;
import app.sctp.targeting.models.HouseholdDetailResponse;
import app.sctp.targeting.models.PreEligibilityVerificationSessionResponse;
import app.sctp.targeting.models.UpdateHouseholdRankRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @Deprecated
    Call<PreEligibilityVerificationSessionResponse> getPreEligibilitySessions(
            @Query("traditional-authority-code") Long taCode,
            @Query("village-cluster-code") Long villageCluster,
            @Query("zone-code") Long zone,
            @Query("village-code") Long village,
            @Query("page") int page
    );

    @Deprecated
    @GET("/targeting/pre-eligibility/sessions/{sessionId}/households")
    Call<HouseholdDetailResponse> getHouseholdsFromPreEligibilitySession(@Path("sessionId") Long sessionId, @Query("page") int page);

    @Deprecated
    @POST("/targeting/pre-eligibility/sessions/{session-id}/households/update-ranks")
    Call<Void> uploadHouseholdSelectionResults(@Path("session-id") Long sessionId, @Body UpdateHouseholdRankRequest request);


    @GET("/targeting/meetings/second-community-meeting")
    Call<CommunityMeetingSessionResponse> getSecondCommunityMeetingSessions(@Query("page") int page, @Query("pageSize") int pageSize);

    @GET("/targeting/meetings/district-meeting")
    Call<CommunityMeetingSessionResponse> getSecondDistrictMeetingSessions(@Query("page") int page, @Query("pageSize") int pageSize);
}

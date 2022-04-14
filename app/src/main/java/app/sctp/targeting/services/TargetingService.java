package app.sctp.targeting.services;


import app.sctp.core.net.api.ext.ApiCall;
import app.sctp.targeting.models.HouseholdDetailResponse;
import app.sctp.targeting.models.TargetingSessionResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TargetingService {
    @GET("/targeting/pre-eligibility/sessions")
    ApiCall<TargetingSessionResponse> getPreEligibilitySessions();

    @GET("/targeting/pre-eligibility/sessions/{sessionId}/households")
    ApiCall<HouseholdDetailResponse> getHouseholdsFromPreEligibilitySession(@Path("sessionId")Long sessionId);
}

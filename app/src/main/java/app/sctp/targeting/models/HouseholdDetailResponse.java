package app.sctp.targeting.models;

import java.util.List;

import app.sctp.core.net.api.PagedResponse;

public class HouseholdDetailResponse extends PagedResponse {
    private List<HouseholdDetails> householdDetails;

    public List<HouseholdDetails> getHouseholdDetails() {
        return householdDetails;
    }

    public void setHouseholdDetails(List<HouseholdDetails> householdDetails) {
        this.householdDetails = householdDetails;
    }
}

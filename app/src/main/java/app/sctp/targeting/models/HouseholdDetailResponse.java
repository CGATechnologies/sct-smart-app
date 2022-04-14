package app.sctp.targeting.models;

import java.util.List;

public class HouseholdDetailResponse {
    private List<HouseholdDetails> householdDetails;

    public List<HouseholdDetails> getHouseholdDetails() {
        return householdDetails;
    }

    public void setHouseholdDetails(List<HouseholdDetails> householdDetails) {
        this.householdDetails = householdDetails;
    }
}

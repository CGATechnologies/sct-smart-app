package app.sctp.targeting.models;

import java.util.List;

public class TargetedHouseholdUpdateRequest {
    private List<HouseholdSelectionResults> statuses;

    public TargetedHouseholdUpdateRequest() {
    }

    public TargetedHouseholdUpdateRequest(List<HouseholdSelectionResults> statuses) {
        this.statuses = statuses;
    }

    public List<HouseholdSelectionResults> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<HouseholdSelectionResults> statuses) {
        this.statuses = statuses;
    }
}

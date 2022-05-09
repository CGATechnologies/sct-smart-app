package app.sctp.targeting.models;

import java.util.List;

public class UpdateHouseholdRankRequest {
    private List<HouseholdSelectionResults> updates;

    public UpdateHouseholdRankRequest() {
    }

    public UpdateHouseholdRankRequest(List<HouseholdSelectionResults> updates) {
        this.updates = updates;
    }

    public List<HouseholdSelectionResults> getUpdates() {
        return updates;
    }

    public void setUpdates(List<HouseholdSelectionResults> updates) {
        this.updates = updates;
    }
}

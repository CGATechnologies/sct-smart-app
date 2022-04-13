package app.sctp.targeting.models;

import androidx.annotation.NonNull;

import java.util.List;

public class HouseholdDetailResponse {
    @NonNull
    private Household household;

    @NonNull
    private List<Individual> members;

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public List<Individual> getMembers() {
        return members;
    }

    public void setMembers(List<Individual> members) {
        this.members = members;
    }
}

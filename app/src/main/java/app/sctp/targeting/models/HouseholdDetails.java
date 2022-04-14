package app.sctp.targeting.models;

import java.util.List;

public class HouseholdDetails {
    private Household household;
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

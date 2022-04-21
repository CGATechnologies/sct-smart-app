package app.sctp.targeting.models;

import java.util.List;

public class HouseholdDetails {
    private Household household;
    private List<Individual> memberDetails;

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public List<Individual> getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(List<Individual> memberDetails) {
        this.memberDetails = memberDetails;
    }
}

package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class HouseholdSelectionResults {

    @ColumnInfo(name = "ranking")
    private Long rank;

    @PrimaryKey
    private Long householdId;

    @ColumnInfo(name = "selection")
    private SelectionStatus cbtStatus;

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public SelectionStatus getCbtStatus() {
        return cbtStatus;
    }

    public void setCbtStatus(SelectionStatus cbtStatus) {
        this.cbtStatus = cbtStatus;
    }
}

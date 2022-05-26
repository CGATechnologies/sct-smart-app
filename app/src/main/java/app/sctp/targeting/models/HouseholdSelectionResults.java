package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class HouseholdSelectionResults {

    @ColumnInfo
    private Long rank;

    @PrimaryKey
    private Long householdId;

    @ColumnInfo
    private SelectionStatus status;

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

    public SelectionStatus getStatus() {
        return status;
    }

    public void setStatus(SelectionStatus status) {
        this.status = status;
    }
}

package app.sctp.enrollment.models;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import app.sctp.targeting.models.SelectionStatus;

public class HouseholdSelectionResults {

    @ColumnInfo
    private Long rank;

    @PrimaryKey
    private Long householdId;

    @ColumnInfo
    private SelectionStatus status;

    @ColumnInfo
    private String rankReason;

    @ColumnInfo
    private String statusReason;

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

    public String getRankReason() {
        return rankReason;
    }

    public void setRankReason(String rankReason) {
        this.rankReason = rankReason;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
}

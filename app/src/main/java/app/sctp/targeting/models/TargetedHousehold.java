package app.sctp.targeting.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.List;
import java.util.Objects;

import app.sctp.core.model.Diffable;

@Entity(tableName = "targeted_households", primaryKeys = {"targeting_session", "household_id"})
public class TargetedHousehold implements Diffable {

    @ColumnInfo(name = "targeting_session", index = true)
    @NonNull
    private Long targetingSession;

    @NonNull
    @ColumnInfo(name = "household_id", index = true)
    private Long householdId;

    @ColumnInfo(name = "ml_code", index = true)
    private Long mlCode;

    @ColumnInfo(name = "form_number", index = true)
    private Long formNumber;

    @ColumnInfo(name = "members")
    private Long members;

    @ColumnInfo(name = "district")
    private String district;

    @ColumnInfo(name = "district_code")
    private Long districtCode;

    @ColumnInfo(name = "ta")
    private String ta;

    @ColumnInfo(name = "ta_code")
    private Long taCode;

    @ColumnInfo(name = "cluster")
    private String cluster;

    @ColumnInfo(name = "cluster_code")
    private Long clusterCode;

    @ColumnInfo(name = "zone")
    private String zone;

    @ColumnInfo(name = "zone_code")
    private Long zoneCode;

    @ColumnInfo(name = "village")
    private String village;

    @ColumnInfo(name = "village_code")
    private Long villageCode;

    @ColumnInfo(name = "village_head")
    private String villageHead;

    private SelectionStatus status;

    private Integer ranking;

    @ColumnInfo(name = "status_change_reason")
    @Nullable
    private String statusChangeReason;

    @ColumnInfo(name = "rank_change_reason")
    @Nullable
    private String rankChangeReason;

    @ColumnInfo(name = "created_at")
    private String createdAt;

    @ColumnInfo(name = "household_head")
    private String householdHead;

    @Ignore
    private List<Individual> memberDetails;

    public void setTargetingSession(@NonNull Long targetingSession) {
        this.targetingSession = targetingSession;
    }

    public void setHouseholdId(@NonNull Long householdId) {
        this.householdId = householdId;
    }

    public void setMlCode(Long mlCode) {
        this.mlCode = mlCode;
    }

    public void setFormNumber(Long formNumber) {
        this.formNumber = formNumber;
    }

    public void setMembers(Long members) {
        this.members = members;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public void setTaCode(Long taCode) {
        this.taCode = taCode;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public void setClusterCode(Long clusterCode) {
        this.clusterCode = clusterCode;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setZoneCode(Long zoneCode) {
        this.zoneCode = zoneCode;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public void setVillageCode(Long villageCode) {
        this.villageCode = villageCode;
    }

    public void setVillageHead(String villageHead) {
        this.villageHead = villageHead;
    }

    public void setStatus(SelectionStatus status) {
        this.status = status;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public void setStatusChangeReason(@Nullable String statusChangeReason) {
        this.statusChangeReason = statusChangeReason;
    }

    public void setRankChangeReason(@Nullable String rankChangeReason) {
        this.rankChangeReason = rankChangeReason;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setHouseholdHead(String householdHead) {
        this.householdHead = householdHead;
    }

    public void setMemberDetails(List<Individual> memberDetails) {
        this.memberDetails = memberDetails;
    }

    @NonNull
    public Long getTargetingSession() {
        return targetingSession;
    }

    @NonNull
    public Long getHouseholdId() {
        return householdId;
    }

    public Long getMlCode() {
        return mlCode;
    }

    public Long getFormNumber() {
        return formNumber;
    }

    public Long getMembers() {
        return members;
    }

    public String getDistrict() {
        return district;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public String getTa() {
        return ta;
    }

    public Long getTaCode() {
        return taCode;
    }

    public String getCluster() {
        return cluster;
    }

    public Long getClusterCode() {
        return clusterCode;
    }

    public String getZone() {
        return zone;
    }

    public Long getZoneCode() {
        return zoneCode;
    }

    public String getVillage() {
        return village;
    }

    public Long getVillageCode() {
        return villageCode;
    }

    public String getVillageHead() {
        return villageHead;
    }

    public SelectionStatus getStatus() {
        return status;
    }

    public Integer getRanking() {
        return ranking;
    }

    @Nullable
    public String getStatusChangeReason() {
        return statusChangeReason;
    }

    @Nullable
    public String getRankChangeReason() {
        return rankChangeReason;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getHouseholdHead() {
        return householdHead;
    }

    public List<Individual> getMemberDetails() {
        return memberDetails;
    }

    public String getMlCodeForPrint() {
        return "ML-" + getMlCode();
    }

    @Override
    public Object getDiffValue() {
        return getHouseholdId();
    }

    @Override
    public boolean contentsSameAs(Diffable diffable) {
        if (!(diffable instanceof TargetedHousehold)) {
            throw new IllegalArgumentException(diffable + " not instance of " + TargetedHousehold.class);
        }
        final TargetedHousehold other = (TargetedHousehold) diffable;
        if (Objects.equals(getDiffValue(), other.getDiffValue())) {
            return this.status == other.status
                    && Objects.equals(this.ranking, other.ranking);
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return "TargetedHousehold{" +
                "formNumber=" + formNumber +
                ", householdHead='" + householdHead + '\'' +
                '}';
    }
}

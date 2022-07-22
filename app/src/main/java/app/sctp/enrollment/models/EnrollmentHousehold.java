package app.sctp.enrollment.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.List;

import app.sctp.core.model.Diffable;
import app.sctp.targeting.models.SelectionStatus;

@Entity(tableName = "enrollment_households", primaryKeys = {"enrollment_session", "household_id"})
public class EnrollmentHousehold implements Diffable {

    @ColumnInfo(name = "enrollment_session", index = true)
    @NonNull
    private Long enrollmentSession;

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
    private List<EnrollmentIndividual> memberDetails;


    @NonNull
    public Long getEnrollmentSession() {
        return enrollmentSession;
    }

    public void setEnrollmentSession(@NonNull Long enrollmentSession) {
        this.enrollmentSession = enrollmentSession;
    }

    @NonNull
    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(@NonNull Long householdId) {
        this.householdId = householdId;
    }

    public Long getMlCode() {
        return mlCode;
    }

    public void setMlCode(Long mlCode) {
        this.mlCode = mlCode;
    }

    public Long getFormNumber() {
        return formNumber;
    }

    public void setFormNumber(Long formNumber) {
        this.formNumber = formNumber;
    }

    public Long getMembers() {
        return members;
    }

    public void setMembers(Long members) {
        this.members = members;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public Long getTaCode() {
        return taCode;
    }

    public void setTaCode(Long taCode) {
        this.taCode = taCode;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public Long getClusterCode() {
        return clusterCode;
    }

    public void setClusterCode(Long clusterCode) {
        this.clusterCode = clusterCode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Long getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(Long zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Long getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(Long villageCode) {
        this.villageCode = villageCode;
    }

    public String getVillageHead() {
        return villageHead;
    }

    public void setVillageHead(String villageHead) {
        this.villageHead = villageHead;
    }

    public SelectionStatus getStatus() {
        return status;
    }

    public void setStatus(SelectionStatus status) {
        this.status = status;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Nullable
    public String getStatusChangeReason() {
        return statusChangeReason;
    }

    public void setStatusChangeReason(@Nullable String statusChangeReason) {
        this.statusChangeReason = statusChangeReason;
    }

    @Nullable
    public String getRankChangeReason() {
        return rankChangeReason;
    }

    public void setRankChangeReason(@Nullable String rankChangeReason) {
        this.rankChangeReason = rankChangeReason;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHouseholdHead() {
        return householdHead;
    }

    public void setHouseholdHead(String householdHead) {
        this.householdHead = householdHead;
    }

    public List<EnrollmentIndividual> getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(List<EnrollmentIndividual> memberDetails) {
        this.memberDetails = memberDetails;
    }

    @Override
    public Object getDiffValue() {
        return null;
    }

    @Override
    public boolean contentsSameAs(Diffable diffable) {
        return Diffable.super.contentsSameAs(diffable);
    }

}

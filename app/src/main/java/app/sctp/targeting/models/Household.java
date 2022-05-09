package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import app.sctp.core.model.Diffable;

@Entity(tableName = "households")
public class Household implements Diffable {

    @PrimaryKey
    @ColumnInfo
    private Long householdId;

    @ColumnInfo
    private String mlCode;

    @ColumnInfo
    private Long sessionId;

    @ColumnInfo
    private String formNumber;

    @ColumnInfo
    private Integer members;

    @ColumnInfo
    private String district;

    @ColumnInfo
    private Long districtCode;

    @ColumnInfo
    private String ta;

    @ColumnInfo
    private Long taCode;

    @ColumnInfo
    private String cluster;

    @ColumnInfo
    private Long clusterCode;

    @ColumnInfo
    private String zone;

    @ColumnInfo
    private Long zoneCode;

    @ColumnInfo
    private String village;

    @ColumnInfo
    private Long villageCode;

    @ColumnInfo
    private String villageHead;

    @ColumnInfo
    private String householdHead;

    @ColumnInfo
    private Integer ranking;

    @ColumnInfo
    private String lastRankingDate;

    @ColumnInfo
    private SelectionStatus selection;

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getLastRankingDate() {
        return lastRankingDate;
    }

    public void setLastRankingDate(String lastRankingDate) {
        this.lastRankingDate = lastRankingDate;
    }

    public SelectionStatus getSelection() {
        return selection;
    }

    public void setSelection(SelectionStatus selection) {
        this.selection = selection;
    }

    public String getMlCode() {
        return mlCode;
    }

    public void setMlCode(String mlCode) {
        this.mlCode = mlCode;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getFormNumber() {
        return formNumber;
    }

    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
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

    public String getHouseholdHead() {
        return householdHead;
    }

    public void setHouseholdHead(String householdHead) {
        this.householdHead = householdHead;
    }

    @Override
    public Object getDiffValue() {
        return getHouseholdId();
    }

    @Override
    public boolean contentsSameAs(Diffable diffable) {
        return diffable.getDiffValue().equals(getDiffValue());
    }

    public String getMlCodeForPrint() {
        return "ML-" + getMlCode();
    }
}

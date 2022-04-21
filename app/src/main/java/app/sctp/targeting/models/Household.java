package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "households")
public class Household {

    @PrimaryKey
    @ColumnInfo
    private Long householdId;

    @ColumnInfo
    private String createdAt;

    @ColumnInfo
    private String modifiedAt;

    @ColumnInfo
    private String deletedAt;

    @ColumnInfo
    private String locationCode;

    @ColumnInfo
    private Long taCode;

    @ColumnInfo
    private Long clusterCode;

    @ColumnInfo
    private Long zoneCode;

    @ColumnInfo
    private Long villageCode;

    @ColumnInfo
    private String villageName;

    @ColumnInfo
    private Long groupVillageHeadCode;

    @ColumnInfo
    private String groupVillageHeadName;

    @ColumnInfo
    private String mlCode;

    @ColumnInfo
    private String ubrCode;

    @ColumnInfo
    private String accountNumber;

    @ColumnInfo
    private Long ubrCsvBatchNumber;

    @ColumnInfo
    private Integer cbtRank;

    @ColumnInfo
    private Integer generalRank;

    @ColumnInfo
    private String lastCbtRanking;

    @ColumnInfo
    private String lastGeneralRanking;

    @ColumnInfo
    private Boolean cbtSelection;

    @ColumnInfo(index = true)
    private Long cbtSessionId;

    @ColumnInfo
    private Long cbtPmt;

    @ColumnInfo
    private String cbtStatus;

    @ColumnInfo
    private double dependencyRatio;

    @ColumnInfo
    private String floorType;

    @ColumnInfo
    private String roofType;

    @ColumnInfo
    private String wallType;

    @ColumnInfo
    private String latrineType;

    @ColumnInfo
    private String houseCondition;

    @ColumnInfo
    private String fuelSource;

    @ColumnInfo
    private String maizeHarvestLasted;

    @ColumnInfo
    private String maizeInGranaryWillLast;

    @ColumnInfo
    private String mealsEatenLastWeek;

    @ColumnInfo
    private Boolean receivesMonetaryAssistance;

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Long getTaCode() {
        return taCode;
    }

    public void setTaCode(Long taCode) {
        this.taCode = taCode;
    }

    public Long getClusterCode() {
        return clusterCode;
    }

    public void setClusterCode(Long clusterCode) {
        this.clusterCode = clusterCode;
    }

    public Long getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(Long zoneCode) {
        this.zoneCode = zoneCode;
    }

    public Long getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(Long villageCode) {
        this.villageCode = villageCode;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Long getGroupVillageHeadCode() {
        return groupVillageHeadCode;
    }

    public void setGroupVillageHeadCode(Long groupVillageHeadCode) {
        this.groupVillageHeadCode = groupVillageHeadCode;
    }

    public String getGroupVillageHeadName() {
        return groupVillageHeadName;
    }

    public void setGroupVillageHeadName(String groupVillageHeadName) {
        this.groupVillageHeadName = groupVillageHeadName;
    }

    public String getMlCode() {
        return mlCode;
    }

    public void setMlCode(String mlCode) {
        this.mlCode = mlCode;
    }

    public String getUbrCode() {
        return ubrCode;
    }

    public void setUbrCode(String ubrCode) {
        this.ubrCode = ubrCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getUbrCsvBatchNumber() {
        return ubrCsvBatchNumber;
    }

    public void setUbrCsvBatchNumber(Long ubrCsvBatchNumber) {
        this.ubrCsvBatchNumber = ubrCsvBatchNumber;
    }

    public Integer getCbtRank() {
        return cbtRank;
    }

    public void setCbtRank(Integer cbtRank) {
        this.cbtRank = cbtRank;
    }

    public Integer getGeneralRank() {
        return generalRank;
    }

    public void setGeneralRank(Integer generalRank) {
        this.generalRank = generalRank;
    }

    public String getLastCbtRanking() {
        return lastCbtRanking;
    }

    public void setLastCbtRanking(String lastCbtRanking) {
        this.lastCbtRanking = lastCbtRanking;
    }

    public String getLastGeneralRanking() {
        return lastGeneralRanking;
    }

    public void setLastGeneralRanking(String lastGeneralRanking) {
        this.lastGeneralRanking = lastGeneralRanking;
    }

    public Boolean getCbtSelection() {
        return cbtSelection;
    }

    public void setCbtSelection(Boolean cbtSelection) {
        this.cbtSelection = cbtSelection;
    }

    public Long getCbtSessionId() {
        return cbtSessionId;
    }

    public void setCbtSessionId(Long cbtSessionId) {
        this.cbtSessionId = cbtSessionId;
    }

    public Long getCbtPmt() {
        return cbtPmt;
    }

    public void setCbtPmt(Long cbtPmt) {
        this.cbtPmt = cbtPmt;
    }

    public String getCbtStatus() {
        return cbtStatus;
    }

    public void setCbtStatus(String cbtStatus) {
        this.cbtStatus = cbtStatus;
    }

    public double getDependencyRatio() {
        return dependencyRatio;
    }

    public void setDependencyRatio(double dependencyRatio) {
        this.dependencyRatio = dependencyRatio;
    }

    public String getFloorType() {
        return floorType;
    }

    public void setFloorType(String floorType) {
        this.floorType = floorType;
    }

    public String getRoofType() {
        return roofType;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType;
    }

    public String getWallType() {
        return wallType;
    }

    public void setWallType(String wallType) {
        this.wallType = wallType;
    }

    public String getLatrineType() {
        return latrineType;
    }

    public void setLatrineType(String latrineType) {
        this.latrineType = latrineType;
    }

    public String getHouseCondition() {
        return houseCondition;
    }

    public void setHouseCondition(String houseCondition) {
        this.houseCondition = houseCondition;
    }

    public String getFuelSource() {
        return fuelSource;
    }

    public void setFuelSource(String fuelSource) {
        this.fuelSource = fuelSource;
    }

    public String getMaizeHarvestLasted() {
        return maizeHarvestLasted;
    }

    public void setMaizeHarvestLasted(String maizeHarvestLasted) {
        this.maizeHarvestLasted = maizeHarvestLasted;
    }

    public String getMaizeInGranaryWillLast() {
        return maizeInGranaryWillLast;
    }

    public void setMaizeInGranaryWillLast(String maizeInGranaryWillLast) {
        this.maizeInGranaryWillLast = maizeInGranaryWillLast;
    }

    public String getMealsEatenLastWeek() {
        return mealsEatenLastWeek;
    }

    public void setMealsEatenLastWeek(String mealsEatenLastWeek) {
        this.mealsEatenLastWeek = mealsEatenLastWeek;
    }

    public Boolean getReceivesMonetaryAssistance() {
        return receivesMonetaryAssistance;
    }

    public void setReceivesMonetaryAssistance(Boolean receivesMonetaryAssistance) {
        this.receivesMonetaryAssistance = receivesMonetaryAssistance;
    }
}

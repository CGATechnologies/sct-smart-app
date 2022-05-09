package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.Locale;

import app.sctp.core.model.Diffable;
import kotlin.jvm.Transient;

@Entity(tableName = "pev_sessions")
public class PreEligibilityVerificationSession implements Diffable {

    @PrimaryKey
    private Long id;
    private String taName;
    private Long programId;

    @ColumnInfo(index = true)
    private Long taCode;
    private Long userId;
    private Long criterionId;

    @ColumnInfo(index = true)
    private Long districtCode;
    private int households;
    private String createdAt;
    private String status;
    private List<Long> clusters;
    private String createdBy;
    private String programName;
    private String districtName;
    private String criterionName;
    private boolean open;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaName() {
        return taName;
    }

    public void setTaName(String taName) {
        this.taName = taName;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getTaCode() {
        return taCode;
    }

    public void setTaCode(Long taCode) {
        this.taCode = taCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCriterionId() {
        return criterionId;
    }

    public void setCriterionId(Long criterionId) {
        this.criterionId = criterionId;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }

    public int getHouseholds() {
        return households;
    }

    public void setHouseholds(int households) {
        this.households = households;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Long> getClusters() {
        return clusters;
    }

    public void setClusters(List<Long> clusters) {
        this.clusters = clusters;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCriterionName() {
        return criterionName;
    }

    public void setCriterionName(String criterionName) {
        this.criterionName = criterionName;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getCreationSummary() {
        return String.format(Locale.US, "%s • %s", createdBy, createdAt);
    }

    @Override
    public Object getDiffValue() {
        return id;
    }
}

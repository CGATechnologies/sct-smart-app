package app.sctp.enrollment.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Locale;
import java.util.Set;

import app.sctp.core.model.Diffable;

@Entity(tableName = "enrollment_sessions")
public class EnrollmentSession implements Diffable{
    public enum SessionStatus {
        Review,
        Closed
    }

    @PrimaryKey
    private Long id;
    private String createdAt;
    private Long doneBy;
    @ColumnInfo(index = true)
    private Long districtCode;
    private Long programId;
    @ColumnInfo(index = true)
    private Long taCode;
    private String timestamp;
    private Long userId;
    private String taName;
    private String doneByName;
    private String programName;
    private String districtName;
    private Long householdCount;
    private SessionStatus status;
    @Ignore
    private Set<Long> clusters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(Long doneBy) {
        this.doneBy = doneBy;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTaName() {
        return taName;
    }

    public void setTaName(String taName) {
        this.taName = taName;
    }

    public String getDoneByName() {
        return doneByName;
    }

    public void setDoneByName(String doneByName) {
        this.doneByName = doneByName;
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

    public Long getHouseholdCount() {
        return householdCount;
    }

    public void setHouseholdCount(Long householdCount) {
        this.householdCount = householdCount;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public String getHouseholdCountSummary() {
        return String.format(Locale.US, "%,d household%s",
                getHouseholdCount(), getHouseholdCount() != 1 ? 's' : "");
    }

    public String getCreationSummary() {
        return String.format(Locale.US, "%s • %s", createdAt, "Creator Name");
    }

    public String getTitle() {
        return String.format(Locale.US, "%s %s", getTaName(), getProgramName());
    }

    public Set<Long> getClusters() {
        return clusters;
    }

    public void setClusters(Set<Long> clusters) {
        this.clusters = clusters;
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

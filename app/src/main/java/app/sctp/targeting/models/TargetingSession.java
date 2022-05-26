package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Locale;
import java.util.Set;

import app.sctp.core.model.Diffable;

@Entity(tableName = "targeting_sessions")
public class TargetingSession implements Diffable {
    public enum SessionStatus {
        Review,
        Closed
    }

    public enum MeetingPhase {
        completed,
        district_meeting,
        second_community_meeting
    }

    @PrimaryKey
    private Long id;
    private String closedAt;
    private String createdAt;
    private Long closedBy;
    private Long createdBy;

    @ColumnInfo(index = true)
    private Long districtCode;

    private Long programId;

    @ColumnInfo(index = true)
    private Long taCode;
    private Long pevSession;

    private MeetingPhase meetingPhase;

    private String communityMeetingTimestamp;
    private String districtMeetingTimestamp;
    private Long districtMeetingUserId;
    private Long communityMeetingUserId;
    private SessionStatus status;

    /**
     * This field is not persisted.
     *
     * @see TargetedCluster
     */
    @Ignore
    private Set<Long> clusters;

    private String taName;
    private String closerName;
    private String programName;
    private String creatorName;
    private String districtName;
    private Long householdCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setCommunityMeetingTimestamp(String communityMeetingTimestamp) {
        this.communityMeetingTimestamp = communityMeetingTimestamp;
    }

    public void setDistrictMeetingTimestamp(String districtMeetingTimestamp) {
        this.districtMeetingTimestamp = districtMeetingTimestamp;
    }

    public Long getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(Long closedBy) {
        this.closedBy = closedBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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

    public Long getPevSession() {
        return pevSession;
    }

    public void setPevSession(Long pevSession) {
        this.pevSession = pevSession;
    }

    public MeetingPhase getMeetingPhase() {
        return meetingPhase;
    }

    public void setMeetingPhase(MeetingPhase meetingPhase) {
        this.meetingPhase = meetingPhase;
    }

    public String getCommunityMeetingTimestamp() {
        return communityMeetingTimestamp;
    }

    public String getDistrictMeetingTimestamp() {
        return districtMeetingTimestamp;
    }

    public Long getDistrictMeetingUserId() {
        return districtMeetingUserId;
    }

    public void setDistrictMeetingUserId(Long districtMeetingUserId) {
        this.districtMeetingUserId = districtMeetingUserId;
    }

    public Long getCommunityMeetingUserId() {
        return communityMeetingUserId;
    }

    public void setCommunityMeetingUserId(Long communityMeetingUserId) {
        this.communityMeetingUserId = communityMeetingUserId;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public Set<Long> getClusters() {
        return clusters;
    }

    public void setClusters(Set<Long> clusters) {
        this.clusters = clusters;
    }

    public String getTaName() {
        return taName;
    }

    public void setTaName(String taName) {
        this.taName = taName;
    }

    public String getCloserName() {
        return closerName;
    }

    public void setCloserName(String closerName) {
        this.closerName = closerName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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

    @Override
    public Object getDiffValue() {
        return getId();
    }

    public String getHouseholdCountSummary() {
        return String.format(Locale.US, "%,d household%s",
                getHouseholdCount(), getHouseholdCount() != 1 ? 's' : "");
    }

    public String getCreationSummary() {
        return String.format(Locale.US, "%s • %s", createdAt, creatorName);
    }

    public String getTitle() {
        return String.format(Locale.US, "%s %s", getTaName(), getProgramName());
    }
}

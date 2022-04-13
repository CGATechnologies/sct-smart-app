package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity
public class Individual {
    @PrimaryKey
    @ColumnInfo
    private Long id;

    @ColumnInfo
    private Long householdId;

    @ColumnInfo
    private String firstName;

    @ColumnInfo
    private String lastName;

    @ColumnInfo
    private String middleName;

    @ColumnInfo
    private Date dateOfBirth;

    @ColumnInfo
    private boolean isEstimatedDob;

    @ColumnInfo
    private String locationCode;

    @ColumnInfo
    private Long ubrCsvBatchNumber;

    @ColumnInfo
    private String status;

    @ColumnInfo
    private String gender;

    @ColumnInfo
    private String maritalStatus;

    @ColumnInfo
    private String individualId;

    @ColumnInfo
    private Date idIssueDate;

    @ColumnInfo
    private Date idExpiryDate;

    @ColumnInfo
    private Date createdAt;

    @ColumnInfo
    private Date modifiedAt;

    @ColumnInfo
    private Date deletedAt;

    @ColumnInfo
    private String phoneNumber;

    @ColumnInfo
    private String highestEducationLevel;

    @ColumnInfo
    private String gradeLevel;

    @ColumnInfo
    private String schoolName;

    @ColumnInfo
    private String sourcedFrom;

    @ColumnInfo
    private String relationshipToHead;

    @ColumnInfo(name = "ubr_household_member_id")
    private Long urbMemberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isEstimatedDob() {
        return isEstimatedDob;
    }

    public void setEstimatedDob(boolean estimatedDob) {
        isEstimatedDob = estimatedDob;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Long getUbrCsvBatchNumber() {
        return ubrCsvBatchNumber;
    }

    public void setUbrCsvBatchNumber(Long ubrCsvBatchNumber) {
        this.ubrCsvBatchNumber = ubrCsvBatchNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Date idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public Date getIdExpiryDate() {
        return idExpiryDate;
    }

    public void setIdExpiryDate(Date idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHighestEducationLevel() {
        return highestEducationLevel;
    }

    public void setHighestEducationLevel(String highestEducationLevel) {
        this.highestEducationLevel = highestEducationLevel;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSourcedFrom() {
        return sourcedFrom;
    }

    public void setSourcedFrom(String sourcedFrom) {
        this.sourcedFrom = sourcedFrom;
    }

    public String getRelationshipToHead() {
        return relationshipToHead;
    }

    public void setRelationshipToHead(String relationshipToHead) {
        this.relationshipToHead = relationshipToHead;
    }

    public Long getUrbMemberId() {
        return urbMemberId;
    }

    public void setUrbMemberId(Long urbMemberId) {
        this.urbMemberId = urbMemberId;
    }
}

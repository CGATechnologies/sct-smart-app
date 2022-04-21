package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Individual {
    @SerializedName("education_level")
    @ColumnInfo(name = "highestEducationLevel")
    private EducationLevel educationLevel;

    @SerializedName("chronic_illness")
    @ColumnInfo
    private ChronicIllness chronicIllness;

    @SerializedName("marital_status")
    @ColumnInfo
    private MaritalStatus maritalStatus;

    @SerializedName("household_code")
    @ColumnInfo
    private String householdCode;

    @SerializedName("orphan_status")
    @ColumnInfo
    private Orphanhood orphanStatus;

    @SerializedName("individual_id")
    @ColumnInfo
    private String individualId;

    @SerializedName("id_issue_date")
    @ColumnInfo
    private String idIssueDate;

    @SerializedName("id_expiry_date")
    @ColumnInfo
    private String idExpiryDate;

    @SerializedName("date_of_birth")
    @ColumnInfo
    private String dateOfBirth;

    @ColumnInfo(name = "relationshipToHead")
    private RelationshipToHead relationship;

    @SerializedName("phone_number")
    @ColumnInfo
    private String phoneNumber;

    @SerializedName("household_id")
    @ColumnInfo(index = true)
    private Long householdId;

    @SerializedName("fit_for_work")
    @ColumnInfo
    private Boolean fitForWork;

    @SerializedName("school_name")
    @ColumnInfo
    private String schoolName;

    @SerializedName("modified_at")
    @ColumnInfo
    private String modifiedAt;

    @SerializedName("grade_level")
    @ColumnInfo
    private GradeLevel gradeLevel;

    @ColumnInfo
    private Disability disability;

    @SerializedName("deleted_at")
    @ColumnInfo
    private String deletedAt;

    @SerializedName("created_at")
    @ColumnInfo
    private String createdAt;

    @ColumnInfo
    @SerializedName("first_name")
    private String firstName;

    @ColumnInfo
    @SerializedName("last_name")
    private String lastName;

    @ColumnInfo
    private int status;

    @ColumnInfo(defaultValue = "")
    private String sourcedFrom;

    @ColumnInfo
    private boolean isEstimatedDob;

    public boolean isEstimatedDob() {
        return isEstimatedDob;
    }

    public void setEstimatedDob(boolean estimatedDob) {
        isEstimatedDob = estimatedDob;
    }

    /*
    These column are unused. A migration can be made but Sqlite has limitations with the ALTER keyword

    sourcedFrom
    isEstimatedDob
    middleName
    locationCode
    ubrCsvBatchNumber
    ubr_household_member_id*/

    private Long locationCode;
    private Long ubrCsvBatchNumber;

    public Long getUbrCsvBatchNumber() {
        return ubrCsvBatchNumber;
    }

    public void setUbrCsvBatchNumber(Long ubrCsvBatchNumber) {
        this.ubrCsvBatchNumber = ubrCsvBatchNumber;
    }

    public Long getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(Long locationCode) {
        this.locationCode = locationCode;
    }

    private String middleName;

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @ColumnInfo(name = "ubr_household_member_id", defaultValue = "0")
    private Long ubrId;


    @ColumnInfo
    private Gender gender;

    @PrimaryKey
    @ColumnInfo
    private Long id;

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public ChronicIllness getChronicIllness() {
        return chronicIllness;
    }

    public void setChronicIllness(ChronicIllness chronicIllness) {
        this.chronicIllness = chronicIllness;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getHouseholdCode() {
        return householdCode;
    }

    public void setHouseholdCode(String householdCode) {
        this.householdCode = householdCode;
    }

    public Orphanhood getOrphanStatus() {
        return orphanStatus;
    }

    public void setOrphanStatus(Orphanhood orphanStatus) {
        this.orphanStatus = orphanStatus;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public String getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(String idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public String getIdExpiryDate() {
        return idExpiryDate;
    }

    public void setIdExpiryDate(String idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public RelationshipToHead getRelationship() {
        return relationship;
    }

    public void setRelationship(RelationshipToHead relationship) {
        this.relationship = relationship;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public Boolean getFitForWork() {
        return fitForWork;
    }

    public void setFitForWork(Boolean fitForWork) {
        this.fitForWork = fitForWork;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Disability getDisability() {
        return disability;
    }

    public void setDisability(Disability disability) {
        this.disability = disability;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourcedFrom() {
        return sourcedFrom;
    }

    public void setSourcedFrom(String sourcedFrom) {
        this.sourcedFrom = sourcedFrom;
    }

    public Long getUbrId() {
        return ubrId;
    }

    public void setUbrId(Long ubrId) {
        this.ubrId = ubrId;
    }
}

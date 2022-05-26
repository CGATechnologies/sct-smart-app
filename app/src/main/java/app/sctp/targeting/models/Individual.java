package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import app.sctp.core.model.Diffable;

@Entity
public class Individual implements Diffable {
    @SerializedName("education_level")
    private EducationLevel educationLevel;

    @SerializedName("chronic_illness")
    private ChronicIllness chronicIllness;

    @SerializedName("marital_status")
    private MaritalStatus maritalStatus;

    @ColumnInfo(index = true)
    @SerializedName("household_code")
    private String householdCode;

    @SerializedName("orphan_status")
    private Orphanhood orphanStatus;

    @ColumnInfo(index = true)
    @SerializedName("individual_id")
    private String individualId;

    @SerializedName("id_issue_date")
    private String idIssueDate;

    @SerializedName("id_expiry_date")
    private String idExpiryDate;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    private RelationshipToHead relationship;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("household_id")
    private Long householdId;

    @SerializedName("fit_for_work")
    private Boolean fitForWork;

    @SerializedName("school_name")
    private String schoolName;

    @SerializedName("modified_at")
    private String modifiedAt;

    @SerializedName("grade_level")
    private GradeLevel gradeLevel;

    private Disability disability;

    @SerializedName("deleted_at")
    private String deletedAt;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    private int status;

    private Gender gender;

    @ColumnInfo(index = true)
    @PrimaryKey
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

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public Long getDiffValue() {
        return getId();
    }
}

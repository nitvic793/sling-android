
package in.sling.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class UserPopulated {

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setWards(List<StudentNested> wards) {
        this.wards = wards;
    }

    public List<ClassRoomNested> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassRoomNested> classes) {
        this.classes = classes;
    }

    @SerializedName("school")
    @Expose
    private School school;

    @SerializedName("wards")
    @Expose
    private List<StudentNested> wards = new ArrayList<StudentNested>();

    @SerializedName("classes")
    @Expose
    private List<ClassRoomNested> classes = new ArrayList<ClassRoomNested>();

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("otpVerified")
    @Expose
    private Boolean otpVerified;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("approved")
    @Expose
    private Boolean approved;
    @SerializedName("isParent")
    @Expose
    private Boolean isParent;
    @SerializedName("isTeacher")
    @Expose
    private Boolean isTeacher;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("employmentStartDate")
    @Expose
    private String employmentStartDate;
    @SerializedName("employeeNumber")
    @Expose
    private String employeeNumber;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("id")
    @Expose
    private String id;

    public String getQuickBloxId() {
        return quickBloxId;
    }

    public void setQuickBloxId(String quickBloxId) {
        this.quickBloxId = quickBloxId;
    }

    @SerializedName("quickBloxId")
    @Expose
    private String quickBloxId;

    public List<StudentNested> getWards(){
        return wards;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber
     * The phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return
     * The otpVerified
     */
    public Boolean getOtpVerified() {
        return otpVerified;
    }

    /**
     *
     * @param otpVerified
     * The otpVerified
     */
    public void setOtpVerified(Boolean otpVerified) {
        this.otpVerified = otpVerified;
    }

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     * The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The approved
     */
    public Boolean getApproved() {
        return approved;
    }

    /**
     *
     * @param approved
     * The approved
     */
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    /**
     *
     * @return
     * The isParent
     */
    public Boolean getIsParent() {
        return isParent;
    }

    /**
     *
     * @param isParent
     * The isParent
     */
    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    /**
     *
     * @return
     * The isTeacher
     */
    public Boolean getIsTeacher() {
        return isTeacher;
    }

    /**
     *
     * @param isTeacher
     * The isTeacher
     */
    public void setIsTeacher(Boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The employmentStartDate
     */
    public String getEmploymentStartDate() {
        return employmentStartDate;
    }

    /**
     *
     * @param employmentStartDate
     * The employmentStartDate
     */
    public void setEmploymentStartDate(String employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    /**
     *
     * @return
     * The employeeNumber
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     *
     * @param employeeNumber
     * The employeeNumber
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /**
     *
     * @return
     * The department
     */
    public String getDepartment() {
        return department;
    }

    /**
     *
     * @param department
     * The department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

}

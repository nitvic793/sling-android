package in.sling.models;

/**
 * Created by nitiv on 5/12/2016.
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Student {

    @SerializedName("classes")
    @Expose
    private List<ClassRoomNested> classes = new ArrayList<ClassRoomNested>();
    @SerializedName("parentInfo")
    @Expose
    private List<User> parentInfo = new ArrayList<User>();
    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = new ArrayList<Review>();
    @SerializedName("school")
    @Expose
    private School school;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("identificationNumber")
    @Expose
    private String identificationNumber;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     *
     * @return
     * The classes
     */
    public List<ClassRoomNested> getClasses() {
        return classes;
    }

    /**
     *
     * @param classes
     * The classes
     */
    public void setClasses(List<ClassRoomNested> classes) {
        this.classes = classes;
    }

    /**
     *
     * @return
     * The parentInfo
     */
    public List<User> getParentInfo() {
        return parentInfo;
    }

    /**
     *
     * @param parentInfo
     * The parentInfo
     */
    public void setParentInfo(List<User> parentInfo) {
        this.parentInfo = parentInfo;
    }

    /**
     *
     * @return
     * The reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     *
     * @param reviews
     * The reviews
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     *
     * @return
     * The school
     */
    public School getSchool() {
        return school;
    }

    /**
     *
     * @param school
     * The school
     */
    public void setSchool(School school) {
        this.school = school;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     *
     * @param dob
     * The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     *
     * @return
     * The identificationNumber
     */
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     *
     * @param identificationNumber
     * The identificationNumber
     */
    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
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



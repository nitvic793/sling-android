package in.sling.models;

/**
 * Created by nitiv on 5/12/2016.
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Review {

    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("student")
    @Expose
    private String student;
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("classRoom")
    @Expose
    private String classRoom;
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
     * The review
     */
    public String getReview() {
        return review;
    }

    /**
     *
     * @param review
     * The review
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     *
     * @return
     * The student
     */
    public String getStudent() {
        return student;
    }

    /**
     *
     * @param student
     * The student
     */
    public void setStudent(String student) {
        this.student = student;
    }

    /**
     *
     * @return
     * The teacher
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     *
     * @param teacher
     * The teacher
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     *
     * @return
     * The classRoom
     */
    public String getClassRoom() {
        return classRoom;
    }

    /**
     *
     * @param classRoom
     * The classRoom
     */
    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
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



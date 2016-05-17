package in.sling.models;

/**
 * Created by nitiv on 5/12/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ReviewPopulated {

    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("student")
    @Expose
    private StudentNested student;
    @SerializedName("teacher")
    @Expose
    private User teacher;
    @SerializedName("classRoom")
    @Expose
    private ClassRoomNested classRoom;
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
    public StudentNested getStudent() {
        return student;
    }

    /**
     *
     * @param student
     * The student
     */
    public void setStudent(StudentNested student) {
        this.student = student;
    }

    /**
     *
     * @return
     * The teacher
     */
    public User getTeacher() {
        return teacher;
    }

    /**
     *
     * @param teacher
     * The teacher
     */
    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    /**
     *
     * @return
     * The classRoom
     */
    public ClassRoomNested getClassRoom() {
        return classRoom;
    }

    /**
     *
     * @param classRoom
     * The classRoom
     */
    public void setClassRoom(ClassRoomNested classRoom) {
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



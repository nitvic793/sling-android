
package in.sling.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ClassRoom {

    @SerializedName("students")
    @Expose
    private List<StudentNested> students = new ArrayList<StudentNested>();
    @SerializedName("notices")
    @Expose
    private List<NoticeBoardBase> notices = new ArrayList<NoticeBoardBase>();

    @SerializedName("teacher")
    @Expose
    private User teacher;

    @SerializedName("school")
    @Expose
    private School school;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("room")
    @Expose
    private String room;
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
     * The students
     */
    public List<StudentNested> getStudents() {
        return students;
    }

    /**
     *
     * @param students
     * The students
     */
    public void setStudents(List<StudentNested> students) {
        this.students = students;
    }

    /**
     *
     * @return
     * The notices
     */
    public List<NoticeBoardBase> getNotices() {
        return notices;
    }

    /**
     *
     * @param notices
     * The notices
     */
    public void setNotices(List<NoticeBoardBase> notices) {
        this.notices = notices;
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
     * The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param subject
     * The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * @return
     * The room
     */
    public String getRoom() {
        return room;
    }

    /**
     *
     * @param room
     * The room
     */
    public void setRoom(String room) {
        this.room = room;
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

    @Override
    public String toString(){
        return room + " (" + subject + ")";
    }

    public ClassRoomNested getNested(){
        ClassRoomNested nested = new ClassRoomNested();
        nested.setId(id);
        nested.setRoom(room);
        nested.setSchool(school.getId());
        nested.setSubject(subject);
        return nested;
    }

}

package in.sling.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class NoticeBoardBase {

//    @SerializedName("classRoom")
//    @Expose
//    private String classRoom;
    @SerializedName("notice")
    @Expose
    private String notice;
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
     * The classRoom
     */
//    public String getClassRoom() {
//        return classRoom;
//    }

    /**
     *
     * @param classRoom
     * The classRoom
     */
//    public void setClassRoom(String classRoom) {
//        this.classRoom = classRoom;
//    }

    /**
     *
     * @return
     * The notice
     */
    public String getNotice() {
        return notice;
    }

    /**
     *
     * @param notice
     * The notice
     */
    public void setNotice(String notice) {
        this.notice = notice;
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

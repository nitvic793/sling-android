package in.sling.models;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by nitiv on 5/12/2016.
 */
public class NoticeBoard extends NoticeBoardBase{
    @SerializedName("classRoom")
    @Expose
    private ClassRoom classRoom;

    /**
     *
     * @return
     * The classRoom
     */
    public ClassRoom getClassRoomObject() {
        return classRoom;
    }

    /**
     *
     * @param classRoom
     * The classRoom
     */
    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }
}

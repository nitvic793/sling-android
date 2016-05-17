package in.sling.models;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rotvic on 5/17/2016.
 */
public class GetWardsResponse {
    @SerializedName("wards")
    @Expose
    private List<Student> wards = new ArrayList<Student>();
    @SerializedName("classes")
    @Expose
    private List<ClassRoom> classes = new ArrayList<ClassRoom>();

    /**
     *
     * @return
     * The wards
     */
    public List<Student> getWards() {
        return wards;
    }

    /**
     *
     * @param wards
     * The wards
     */
    public void setWards(List<Student> wards) {
        this.wards = wards;
    }

    /**
     *
     * @return
     * The classes
     */
    public List<ClassRoom> getClasses() {
        return classes;
    }

    /**
     *
     * @param classes
     * The classes
     */
    public void setClasses(List<ClassRoom> classes) {
        this.classes = classes;
    }
}

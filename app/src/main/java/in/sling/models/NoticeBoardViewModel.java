package in.sling.models;

/**
 * Created by Rotvic on 5/17/2016.
 */
public class NoticeBoardViewModel {
    private String notice;
    private String teacher;
    private String clas;
    private String id;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    private String createdAt;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public NoticeBoardViewModel() {
    }

    public String getNotice(){
        return notice;
    }

    public void setNotice(String n){
        notice = n;
    }

    public String getTeacher(){
        return teacher;
    }

    public void setTeacher(String t){
        teacher = t;
    }

    public String getClassRoom(){
        return clas;
    }

    public void setClassRoom(String classRoom){
        clas = classRoom;
    }

}

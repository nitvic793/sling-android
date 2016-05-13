package in.sling.services;
import java.util.List;

import in.sling.models.ClassRoom;
import in.sling.models.Data;
import in.sling.models.NoticeBoard;
import in.sling.models.NoticeBoardBase;
import in.sling.models.School;
import in.sling.models.Student;
import in.sling.models.User;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;
/**
 * Created by nitiv on 5/12/2016.
 */
public interface SlingService{

    @GET("school")
    Call<Data<List<School>>> getSchools();

    @GET("user")
    Call<Data<List<User>>> getUsers();

    @GET("classroom")
    Call<Data<List<ClassRoom>>> getClassRooms();

    @GET("noticeboard")
    Call<Data<List<NoticeBoardBase>>> getNotices();

    @GET("student")
    Call<Data<List<Student>>> getStudents();
}

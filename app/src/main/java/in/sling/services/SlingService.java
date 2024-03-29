package in.sling.services;
import java.util.List;

import in.sling.models.ClassRoom;
import in.sling.models.ClassRoomNested;
import in.sling.models.Data;
import in.sling.models.GetWardsResponse;
import in.sling.models.NoticeBoard;
import in.sling.models.NoticeBoardBase;
import in.sling.models.OtpResponse;
import in.sling.models.PasswordPayload;
import in.sling.models.Review;
import in.sling.models.ReviewPopulated;
import in.sling.models.School;
import in.sling.models.Student;
import in.sling.models.Token;
import in.sling.models.User;
import in.sling.models.UserPopulated;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @GET("user")
    Call<Data<List<User>>> getUser(@Query("id")String id);

    @GET("classroom")
    Call<Data<List<ClassRoom>>> getClassRooms();

    @GET("classroom")
    Call<Data<List<ClassRoom>>> getClassRoomsByTeacher(@Query("teacher")String teacher);

    @GET("classroom")
    Call<Data<List<ClassRoom>>> getClassRoomsById(@Query("id")String id);

    @GET("classroom")
    Call<Data<List<ClassRoom>>> getClassRooms(@Query("school")String school);

    @GET("noticeboard")
    Call<Data<List<NoticeBoardBase>>> getNotices();

    @GET("noticeboard/get")
    Call<Data<List<ClassRoomNested>>> getNoticeBoards(@Query("student")String student);

    @GET("noticeboard")
    Call<Data<List<NoticeBoardBase>>> getNotices(@Query("classRoom")String classroom);

    @GET("student")
    Call<Data<List<Student>>> getStudents();

    @GET("review")
    Call<Data<List<Review>>> getReviews();

    @GET("review")
    Call<Data<List<Review>>> getReviews(@Query("student")String student);

    @GET("review")
    Call<Data<List<ReviewPopulated>>> getReviewsByTeacher(@Query("teacher")String teacher);

    @POST("auth/signin")
    Call<Data<Token>> login(@Query("email") String email, @Query("password") String password);

    @GET("student/getwards")
    Call<Data<GetWardsResponse>> getWards();

    @POST("noticeboard")
    Call<Data<NoticeBoardBase>> createNotice(@Body NoticeBoardBase noticeBoard);

    @POST("review")
    Call<Data<Review>> createReview(@Body Review review);

    @POST("otp/generateOtp")
    Call<Data<OtpResponse>> generateOtp(@Query("phoneNumber") String phoneNumber);

    @POST("otp/verify")
    Call<Data<Token>> verify(@Query("phoneNumber") String phoneNumber, @Query("otp")String otp);

    @POST("user/{id}")
    Call<Data<UserPopulated>> updateQuickBloxId(@Path("id")String id, @Query("quickbloxId")String quickbloxId);

    @GET("user")
    Call<Data<List<UserPopulated>>> getAllParents(@Query("school")String school,@Query("isParent")boolean isParent);

    @GET("user")
    Call<Data<List<UserPopulated>>> getAllTeachers(@Query("school")String school,@Query("isTeacher")boolean isTeacher);

    @POST("user/{id}")
    Call<Data<UserPopulated>> updatePassword(@Path("id")String id, @Body PasswordPayload password);

}

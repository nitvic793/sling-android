package in.sling.services;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.ColorRes;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.security.auth.callback.Callback;

import in.sling.adapters.NoticeBoardAdapter;
import in.sling.models.ClassRoom;
import in.sling.models.ClassRoomNested;
import in.sling.models.Data;
import in.sling.models.GetWardsResponse;
import in.sling.models.NoticeBoard;
import in.sling.models.NoticeBoardBase;
import in.sling.models.Review;
import in.sling.models.ReviewPopulated;
import in.sling.models.Student;
import in.sling.models.StudentNested;
import in.sling.models.User;
import in.sling.models.UserPopulated;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rotvic on 5/16/2016.
 */
public class DataService {
    SharedPreferences preferences;
    SlingService service;
    UserPopulated user;
    Gson gson = new Gson();
    ArrayList<Student> students = new ArrayList<Student>();
    ArrayList<ClassRoom> classes = new ArrayList<ClassRoom>();
    ArrayList<NoticeBoardBase> notices = new ArrayList<NoticeBoardBase>();

    public DataService(SharedPreferences pref){
        String token = pref.getString("token","");
        service = RestFactory.createService(token);
        preferences = pref;
    }

    public void setUser(UserPopulated u){
        user = u;
        preferences.edit().putString("user",gson.toJson(u)).apply();
    }

    public UserPopulated getUser(){
        return gson.fromJson(preferences.getString("user",""),UserPopulated.class);
    }


    public void LoadAllRequiredData(ProgressDialog progress){
        //Parent - Wards, Classes, NoticeBoards, Reviews
        //Teacher - Classes, NoticeBoards,Reviews
        progress.setTitle("Loading");
        progress.setMessage("Requesting required data...");
        progress.show();
        if(preferences.getString("userType","").equalsIgnoreCase("parent")){
            LoadParentData(progress);
        }
        else if(preferences.getString("userType","").equalsIgnoreCase("teacher")){
            LoadTeacherData(progress);
        }
    }


    private void LoadParentData(final ProgressDialog progress){
        service.getWards().enqueue(new retrofit2.Callback<Data<GetWardsResponse>>() {
            @Override
            public void onResponse(Call<Data<GetWardsResponse>> call, Response<Data<GetWardsResponse>> response) {
                GetWardsResponse result = response.body().getData();
                classes.addAll(result.getClasses());
                Map<String, ClassRoom> classRoomHashMap = new HashMap<String, ClassRoom>();
                for(ClassRoom cl: classes){
                    classRoomHashMap.put(cl.getId(),cl);
                    notices.addAll(cl.getNotices());
                }

//                classes.clear();
//                for(ClassRoom cl: classRoomHashMap.values()){
//                    classes.add(cl);
//                }
//                //classes.addAll(classRoomHashMap.values());
//                Map<String, NoticeBoardBase> noticeMap = new HashMap<String, NoticeBoardBase>();
//                for(NoticeBoardBase nb: notices){
//                    noticeMap.put(nb.getId(), nb);
//                }
//                notices.clear();
//                for(NoticeBoardBase nb: noticeMap.values()){
//                    notices.add(nb);
//                }
                preferences.edit().putString("classes",gson.toJson(classes)).apply();
                preferences.edit().putString("notices",gson.toJson(notices)).apply();
                preferences.edit().putString("wards", gson.toJson(result.getWards())).apply();
                progress.dismiss();
                Log.i("Test", response.message());
            }

            @Override
            public void onFailure(Call<Data<GetWardsResponse>> call, Throwable t) {
                Log.e("Error", t.getMessage());
                progress.dismiss();
            }
        });
    }

    private void LoadTeacherData(final ProgressDialog progress){

        service.getReviewsByTeacher(user.getId()).enqueue(new retrofit2.Callback<Data<List<ReviewPopulated>>>() {
            @Override
            public void onResponse(Call<Data<List<ReviewPopulated>>> call, Response<Data<List<ReviewPopulated>>> response) {
                //Store all reviews
                Log.e("Data", response.body().getMessage());
                preferences.edit().putString("reviews",gson.toJson(response.body().getData())).apply();
                loadTeacherClasses(progress);
            }

            @Override
            public void onFailure(Call<Data<List<ReviewPopulated>>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    public ArrayList<ClassRoom> getClasses(){
        return gson.fromJson(preferences.getString("classes", ""),(new ArrayList<ClassRoom>()).getClass());
    }


    public List<NoticeBoardBase> getNotices(){
        Log.i("Data", preferences.getString("notices", ""));
        NoticeBoardBase[] arr  = gson.fromJson(preferences.getString("notices", ""), NoticeBoardBase[].class);
        return Arrays.asList(arr);
    }

    public ClassRoom findClassRoom(String id){
        HashMap<String, ClassRoom> t = new HashMap<>();
        t = gson.fromJson(preferences.getString("classes",""),t.getClass());
        return t.get(id);
    }

    private void loadTeacherClasses(final ProgressDialog progress){
        service.getClassRoomsByTeacher(user.getId()).enqueue(new retrofit2.Callback<Data<List<ClassRoom>>>() {
            @Override
            public void onResponse(Call<Data<List<ClassRoom>>> call, Response<Data<List<ClassRoom>>> response) {
                //Get students and Notices
                classes.addAll(response.body().getData());
                Map<String, ClassRoom> classRoomHashMap = new HashMap<String, ClassRoom>();
                for(ClassRoom cl: classes){
                    classRoomHashMap.put(cl.getId(),cl);
                    notices.addAll(cl.getNotices());
                }

                classes.clear();
                for(ClassRoom cl: classRoomHashMap.values()){
                    classes.add(cl);
                }
                //classes.addAll(classRoomHashMap.values());
                Map<String, NoticeBoardBase> noticeMap = new HashMap<String, NoticeBoardBase>();
                for(NoticeBoardBase nb: notices){
                    noticeMap.put(nb.getId(), nb);
                }
                notices.clear();
                for(NoticeBoardBase nb: noticeMap.values()){
                    notices.add(nb);
                }

                preferences.edit().putString("classes",gson.toJson(classes)).apply();
                preferences.edit().putString("notices",gson.toJson(notices)).apply();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Data<List<ClassRoom>>> call, Throwable t) {
                Log.e("Error", t.getMessage());
                progress.dismiss();
            }
        });

    }
}
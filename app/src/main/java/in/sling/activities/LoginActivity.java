package in.sling.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import in.sling.R;
import in.sling.models.ClassRoom;
import in.sling.models.Data;
import in.sling.models.NoticeBoard;
import in.sling.models.NoticeBoardBase;
import in.sling.models.School;
import in.sling.models.Student;
import in.sling.models.User;
import in.sling.services.RestFactory;
import in.sling.services.SlingService;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Intent intent = new Intent(this,HomeActivity.class);
        Button button = (Button)findViewById(R.id.register_continue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        TextView newUserText = (TextView)findViewById(R.id.new_user_text);
        final Intent registerIntent = new Intent(this,RegisterActivity.class);
        newUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerIntent);
            }
        });
        testData();
    }

    public void testData(){
        try
        {
            SlingService service = RestFactory.createService("JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6IjU3MjcwODQ1MjdkZDlhYjQ0MzM1MjA1NiIsImlhdCI6MTQ2MzExMjcxMiwiZXhwIjoxNDYzMTk5MTEyfQ.u9OvwJ2SLBOsHCSgjGlS2AJEjj5APA2161KL0yGusnyNQgWiy6LZhVWvW6FmCPgO_Boh7mTulUda-6iKh1ETjA");
            service.getStudents().enqueue(new Callback<Data<List<Student>>>() {
                @Override
                public void onResponse(Call<Data<List<Student>>> call, Response<Data<List<Student>>> response) {
                    Data<List<Student>> data = response.body();
                   // Log.i("Data", response.body().getData().get(0).getName());
                }

                @Override
                public void onFailure(Call<Data<List<Student>>> call, Throwable t) {
                    //Log.e("Error",t.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            Log.e("Error",e.getMessage());
        }
    }
}

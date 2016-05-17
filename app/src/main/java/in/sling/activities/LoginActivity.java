package in.sling.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.List;

import in.sling.R;
import in.sling.models.ClassRoom;
import in.sling.models.ClassRoomNested;
import in.sling.models.Data;
import in.sling.models.Token;
import in.sling.models.User;
import in.sling.models.UserPopulated;
import in.sling.services.CustomCallback;
import in.sling.services.DataService;
import in.sling.services.RestFactory;
import in.sling.services.SlingService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SlingService service;
    EditText emailText;
    EditText passwordText;
    Intent registerIntent;
    Intent intent;
    DataService dataService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        JodaTimeAndroid.init(this);
        super.onCreate(savedInstanceState);
        registerIntent = new Intent(this,RegisterActivity.class);
        intent = new Intent(this,HomeActivity.class);

        setContentView(R.layout.activity_login);
        Button button = (Button)findViewById(R.id.register_continue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        TextView newUserText = (TextView)findViewById(R.id.new_user_text);
        newUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerIntent);
            }
        });
        emailText = (EditText)findViewById(R.id.email);
        passwordText = (EditText)findViewById(R.id.password);
    }

    private void storeToken(String token){
       SharedPreferences preferences = getSharedPreferences("in.sling", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", "JWT " + token);
        editor.apply();
       preferences.getString("token", "");
        Log.i("TokenSP", preferences.getString("token", ""));
    }

    private void storeUserId(String userId){
        SharedPreferences preferences = getSharedPreferences("in.sling", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userId", userId);
        editor.apply();
    }

    private void storeUserType(String type){
        SharedPreferences preferences = getSharedPreferences("in.sling", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userType", type);
        editor.apply();
    }

    public void loginUser(){
        final ProgressDialog progress = new ProgressDialog(this);
        final ProgressDialog dataLoadProgress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Logging in...");
        progress.show();

        try
        {
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            service = RestFactory.createService();
            service.login(email,password).enqueue(new Callback<Data<Token>>() {
                @Override
                public void onResponse(Call<Data<Token>> call, Response<Data<Token>> response) {
                    String token;
                    if(response.body()!=null) {
                        token = response.body().getData().getToken();
                        Log.i("Token", response.body().getMessage());
                        storeToken(response.body().getData().getToken());
                        storeUserId(response.body().getData().getUser().getId());
                        dataService = new DataService(getSharedPreferences("in.sling", Context.MODE_PRIVATE));
                        dataService.setUser(response.body().getData().getUser());
                        UserPopulated x = dataService.getUser();
                        if(response.body().getData().getUser().getIsParent()){
                            storeUserType("parent");
                        }
                        else if(response.body().getData().getUser().getIsTeacher())
                        {
                            storeUserType("teacher");
                        }
                        else{
                            storeUserType("unknown");
                        }
                        progress.dismiss();
                        progress.setTitle("Loading");
                        progress.setMessage("Gathering required data...");
                        progress.show();
                        dataService.LoadAllRequiredData(new CustomCallback() {
                            @Override
                            public void onCallback() {
                                startActivity(intent);
                                progress.dismiss();
                            }
                        });
                        service = RestFactory.createService(token);
                    }
                    else
                    {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(),"Invalid email/password",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Data<Token>> call, Throwable t) {
                   // Log.e("Error",t.getMessage());
                    Toast.makeText(getApplicationContext(),"Unable to make login request",Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            });
        }
        catch (Exception e)
        {
            progress.dismiss();
            Log.e("Error",e.getMessage());
        }
    }
}

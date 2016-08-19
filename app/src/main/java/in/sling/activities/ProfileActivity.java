package in.sling.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import org.w3c.dom.Text;

import in.sling.Constants;
import in.sling.R;
import in.sling.models.UserPopulated;
import in.sling.services.DataService;

public class ProfileActivity extends AppCompatActivity {

    DataService dataService;
    TextView mProfileNameText;
    TextView mEmailText;
    TextView mPhoneText;
    TextView mProfileTypeText;
    UserPopulated slingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new FlurryAgent.Builder()
                .withLogEnabled(false)
                .build(this, Constants.FLURRY_KEY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mProfileNameText = (TextView)findViewById(R.id.profile_name_text);
        mEmailText = (TextView)findViewById(R.id.tvNumber3);
        mPhoneText = (TextView)findViewById(R.id.tvNumber1);
        mProfileTypeText = (TextView)findViewById(R.id.profile_activity_type_text);
        dataService = new DataService(getSharedPreferences("in.sling", Context.MODE_PRIVATE));
        slingUser = dataService.getUser();
        loadData();
    }

    private void loadData(){
        String name = slingUser.getFirstName() + " " + slingUser.getLastName();
        String phone = slingUser.getPhoneNumber();
        String email = slingUser.getEmail();
        String profileType="";
        if(slingUser.getIsParent()){
            profileType = "Parent Profile";
        }
        else if(slingUser.getIsTeacher()){
            profileType = "Teacher Profile";
        }
        mProfileTypeText.setText(profileType);
        mProfileNameText.setText(name);
        mPhoneText.setText(phone);
        mEmailText.setText(email);
    }
}

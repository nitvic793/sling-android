package in.sling.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

import java.util.ArrayList;
import java.util.List;

import in.sling.Constants;
import in.sling.R;
import in.sling.models.Data;
import in.sling.models.PasswordPayload;
import in.sling.models.UserPopulated;
import in.sling.services.DataService;
import in.sling.services.RestFactory;
import in.sling.services.SlingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class UpdatePasswordActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    // UI references.
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new FlurryAgent.Builder()
                .withLogEnabled(false)
                .build(this, Constants.FLURRY_KEY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        // Set up the login form.
        mPassword = (EditText) findViewById(R.id.update_password);
        //populateAutoComplete();
        final DataService dataService = new DataService(getSharedPreferences("in.sling", Context.MODE_PRIVATE));
        final SlingService api = dataService.getAPIService();
        mPasswordConfirm = (EditText) findViewById(R.id.update_password_confirm);
        final UpdatePasswordActivity activity = this;
        Button mChangePassoword = (Button) findViewById(R.id.change_password_button);
        mChangePassoword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String password = mPassword.getText().toString();
                    String confirmPassword= mPasswordConfirm.getText().toString();
                    if(!password.contentEquals(confirmPassword)){
                        new AlertDialog.Builder(activity)
                                .setTitle("Uh Oh!")
                                .setMessage("Passwords don't match")
                                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                    else{
                        PasswordPayload passwordPayload = new PasswordPayload();
                        passwordPayload.setPassword(password);
                        final ProgressDialog progressDialog = new ProgressDialog(activity);
                        progressDialog.setMessage("Updating password");
                        progressDialog.show();
                        api.updatePassword(dataService.getUser().getId(), passwordPayload).enqueue(new Callback<Data<UserPopulated>>() {
                            @Override
                            public void onResponse(Call<Data<UserPopulated>> call, Response<Data<UserPopulated>> response) {
                                progressDialog.dismiss();
                                Toast.makeText(activity, "Updated password successfully",Toast.LENGTH_SHORT).show();

                                activity.finish();
                            }

                            @Override
                            public void onFailure(Call<Data<UserPopulated>> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.e("UpdatePassword", t.getMessage());
                                Toast.makeText(activity, "Could not update password",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Unexpected error occurred",Toast.LENGTH_SHORT);
                }

            }
        });

    }



}


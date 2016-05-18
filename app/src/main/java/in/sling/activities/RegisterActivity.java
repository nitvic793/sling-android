package in.sling.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.sling.R;
import in.sling.models.Data;
import in.sling.models.OtpResponse;
import in.sling.models.Token;
import in.sling.models.UserPopulated;
import in.sling.services.CustomCallback;
import in.sling.services.DataService;
import in.sling.services.RestFactory;
import in.sling.services.SlingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mMobileNumberView;
    private TextView mResendOtp;
    private EditText mOtp;
    DataService dataService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mMobileNumberView = (EditText) findViewById(R.id.mobile_number);
        mResendOtp = (TextView)findViewById(R.id.resend_otp_textview);
        mOtp = (EditText)findViewById(R.id.otp);
        mMobileNumberView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.register_continue);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        findViewById(R.id.register_finish_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOtp(mMobileNumberView.getText().toString(), mOtp.getText().toString(),
                        new CustomCallback() {
                            @Override
                            public void onCallback() {
                                Toast.makeText(getApplicationContext(),"Log in...", Toast.LENGTH_SHORT).show();
                                // Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                               // startActivity(intent);
                            }
                        }, null);
            }
        });

        mResendOtp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                generateOtp(mMobileNumberView.getText().toString(), null, null);
            }
        });

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

    public void verifyOtp(String phoneNumber, String otp, final CustomCallback onSuccess, final CustomCallback onFailure){
        SlingService api = RestFactory.createService();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Verifying OTP");
        progressDialog.show();
        api.verify(phoneNumber,otp).enqueue(new Callback<Data<Token>>() {
            @Override
            public void onResponse(Call<Data<Token>> call, Response<Data<Token>> response) {

                if(response.body()!=null) {
                    String token = response.body().getData().getToken();
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
                    progressDialog.dismiss();
                    progressDialog.setTitle("Loading");
                    progressDialog.setMessage("Gathering required data...");
                    progressDialog.show();
                    dataService.LoadAllRequiredData(new CustomCallback() {
                        @Override
                        public void onCallback() {
                            if(onSuccess!=null){
                                onSuccess.onCallback();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Invalid data",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Data<Token>> call, Throwable t) {
                if(onFailure!=null){
                    onFailure.onCallback();
                }
                Toast.makeText(getApplicationContext(),"OTP could not be verified", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void generateOtp(String phoneNumber, final CustomCallback callback, final CustomCallback errorCallback){
        SlingService api = RestFactory.createService();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Requesting OTP");
        progressDialog.show();
        api.generateOtp(phoneNumber).enqueue(new Callback<Data<OtpResponse>>() {
            @Override
            public void onResponse(Call<Data<OtpResponse>> call, Response<Data<OtpResponse>> response) {
                if(callback!=null)
                callback.onCallback();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Data<OtpResponse>> call, Throwable t) {
                if(errorCallback!=null)
                errorCallback.onCallback();
                Toast.makeText(getApplicationContext(),"Could not send OTP", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        SlingService api =  RestFactory.createService();
        if (mAuthTask != null) {
            return;
        }

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mMobileNumberView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Field cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        generateOtp(password, new CustomCallback() {
                    @Override
                    public void onCallback() {
                        findViewById(R.id.layout_register).setVisibility(View.GONE);
                        findViewById(R.id.layout_otp).setVisibility(View.VISIBLE);
                    }
                }, null);

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            focusView = mMobileNumberView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
            } else {
                mMobileNumberView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

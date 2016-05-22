package in.sling;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.quickblox.core.QBSettings;
import com.quickblox.core.server.BaseService;

/**
 * Created by nitiv on 5/20/2016.
 */
public class SlingApplication extends Application {

    static final String APP_ID = "36739";
    static final String AUTH_KEY = "BLkjN8--tHLn8yz";
    static final String AUTH_SECRET = "a8pxRnCPW7VjBWC";
    static final String ACCOUNT_KEY = "QyYej5Hd3zk2PUgQiqy7";
    SharedPreferences mPref;

    private static SlingApplication ourInstance = new SlingApplication();

    public static SlingApplication getInstance() {
        return ourInstance;
    }

    public SlingApplication(){

    }

    @Override
    public void onCreate() {

        super.onCreate();
        Log.i("App","Created");
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }
}

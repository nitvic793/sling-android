package in.sling.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBPrivateChat;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.listeners.QBPrivateChatManagerListener;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.messages.QBPushNotifications;
import com.quickblox.messages.model.QBEnvironment;
import com.quickblox.messages.model.QBEvent;
import com.quickblox.messages.model.QBNotificationChannel;
import com.quickblox.messages.model.QBNotificationType;
import com.quickblox.messages.model.QBPushType;
import com.quickblox.messages.model.QBSubscription;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import in.sling.adapters.ChatDialogsAdapter;
import in.sling.models.ChatDialogViewModel;
import in.sling.models.Data;
import in.sling.models.User;
import in.sling.models.UserPopulated;
import in.sling.services.CustomCallback;
import in.sling.services.DataService;
import in.sling.services.RestFactory;
import in.sling.services.SlingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nitiv on 5/20/2016.
 */
public class Chat {

    private static Chat chatInstance;
    QBChatService chatService = QBChatService.getInstance();
    UserPopulated slingUser;
    QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
    QBUser mUser;
    SharedPreferences preferences;
    SlingService api;
    DataService dataService;
    ArrayList<QBUser> chatUsers = new ArrayList<>();
    ArrayList<ChatDialogViewModel> dialogs = new ArrayList<>();
    TelephonyManager systemService;
    ContentResolver contentResolver;

    public static void initialize(UserPopulated user, SharedPreferences preferences, Activity activity){
        chatInstance = new Chat(user, preferences);
        chatInstance.systemService = (TelephonyManager)activity.getSystemService(
                Context.TELEPHONY_SERVICE);
        chatInstance.contentResolver = activity.getContentResolver();
    }
    public static Chat getChatInstance(){
        return chatInstance;
    }

    private Chat(UserPopulated user, SharedPreferences preferences){
        slingUser = user;
        this.preferences = preferences;
        dataService = new DataService(preferences);
        api = RestFactory.createService(preferences.getString("token",""));
        requestBuilder.setLimit(100);
       // chatSignUpOrIn(slingUser);
    }
    //
// Subscribe to Push Notifications
    public void subscribeToPushNotifications(String registrationID,final CustomCallback cb) {
        QBSubscription subscription = new QBSubscription(QBNotificationChannel.GCM);
        subscription.setEnvironment(QBEnvironment.PRODUCTION);
        //
        String deviceId;
        final TelephonyManager mTelephony = systemService;
        if (mTelephony.getDeviceId() != null) {
            deviceId = mTelephony.getDeviceId(); //*** use for mobiles
        } else {
            deviceId = Settings.Secure.getString(contentResolver,
                    Settings.Secure.ANDROID_ID); //*** use for tablets
        }
        subscription.setDeviceUdid(deviceId);
        //
        subscription.setRegistrationID(registrationID);
        //
        QBPushNotifications.createSubscription(subscription, new QBEntityCallback<ArrayList<QBSubscription>>() {
            @Override
            public void onSuccess(ArrayList<QBSubscription> subscriptions, Bundle args) {
                Log.i("Push","Subscribed successfully");
                cb.onCallback();
            }

            @Override
            public void onError(QBResponseException error) {
                Log.e("Push","Error " + error.getMessage());
                cb.onCallback();
            }
        });
    }

    public void sendNotifications(StringifyArrayList<Integer> userIds){
        Log.i("Push",userIds.get(0).toString());
        QBEvent event = new QBEvent();
        event.setUserIds(userIds);
        event.setEnvironment(QBEnvironment.PRODUCTION);
        event.setNotificationType(QBNotificationType.PUSH);
        event.setPushType(QBPushType.GCM);
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("data.message", "Hello");
        data.put("data.type", "welcome message");
        event.setMessage(data);

        QBPushNotifications.createEvent(event, new QBEntityCallback<QBEvent>() {
            @Override
            public void onSuccess(QBEvent qbEvent, Bundle args) {
                // sent
            }

            @Override
            public void onError(QBResponseException errors) {

            }
        });
    }

    public void createSession(final CustomCallback cb){
        final QBUser user = new QBUser(slingUser.getId(), slingUser.getId());
        //user.setFullName(slingUser.getFirstName() + " " + slingUser.getLastName());
        if(slingUser.getQuickBloxId()==null){
            QBAuth.createSession(new QBEntityCallback<QBSession>() {
                @Override
                public void onSuccess(final QBSession session, Bundle params) {
                    // success, login to chat
                    QBUsers.signUp(user, new QBEntityCallback<QBUser>() {
                        @Override
                        public void onSuccess(final QBUser user, Bundle args) {
                            Log.i("QB","Sign up "+ user.getId() );
                            mUser = user;
                            slingUser.setQuickBloxId(user.getId().toString());

                            dataService.setUser(slingUser);
                            mUser.setId(user.getId());
                            api.updateQuickBloxId(slingUser.getId(),user.getId().toString()).enqueue(new Callback<Data<UserPopulated>>() {
                                @Override
                                public void onResponse(Call<Data<UserPopulated>> call, Response<Data<UserPopulated>> response) {
                                    Log.i("Chat", "User signed up");
                                    cb.onCallback();
                                }
                                @Override
                                public void onFailure(Call<Data<UserPopulated>> call, Throwable t) {
                                    cb.onCallback();
                                }
                            });
                        }
                        @Override
                        public void onError(QBResponseException error) {
                            // error
                            Log.e("Chat","Error " + error.getMessage());
                            QBAuth.createSession(user, new QBEntityCallback<QBSession>() {
                                @Override
                                public void onSuccess(QBSession qbSession, Bundle bundle) {
                                    mUser = user;
                                    mUser.setId(qbSession.getUserId());
                                    cb.onCallback();
                                    Log.i("Chat", "Session created");
                                }

                                @Override
                                public void onError(QBResponseException e) {
                                    Log.e("Chat Error", e.getMessage());
                                    cb.onCallback();
                                }
                            });
                        }
                    });
                }

                @Override
                public void onError(QBResponseException errors) {
                    Log.e("Chat","Error " + errors.getMessage());
                    cb.onCallback();
                }
            });
        }
        else{
            QBAuth.createSession(user, new QBEntityCallback<QBSession>() {
                @Override
                public void onSuccess(QBSession qbSession, Bundle bundle) {
                    mUser = user;
                    mUser.setId(qbSession.getUserId());
                    cb.onCallback();
                    Log.i("Chat", "Session created");

                }

                @Override
                public void onError(QBResponseException e) {
                    Log.e("Chat Error", e.getMessage());
                    cb.onCallback();
                }
            });
        }
    }

    public void loadUsers(List<User> userList, CustomCallback cb){
        ArrayList<User> users = new ArrayList<>(userList);
        ArrayList<String> logins = new ArrayList<>();
        for(User u: users){
            logins.add(u.getId());
        }

    }

    public QBUser getCurrentUser(){
        return mUser;
    }

    public QBChatService getChatService(){
        return chatService;
    }

    public QBUser getQbUser(String id) throws QBResponseException{
        return QBUsers.getUserByLogin(id);
    }

    public void createDialog(int opponentId, QBEntityCallback<QBDialog> cb){
        try
        {
            chatService.getPrivateChatManager().createDialog(opponentId,cb);
        }
        catch (Exception e)
        {
            cb.onError(null);
        }
    }

    public QBPrivateChatManager getPrivateChatManager(){
        return chatService.getPrivateChatManager();
    }

    public void loginChat(final CustomCallback cb){
        if(chatService.isLoggedIn()){
            cb.onCallback();
            return;
        }
        chatService.login(mUser, new QBEntityCallback() {
            @Override
            public void onSuccess(Object o, Bundle bundle) {
                cb.onCallback();
                Log.i("Chat","Logged in");
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("Chat Error", e.getMessage());
            }
        });
    }



    private void chatSignUpOrIn(final UserPopulated user){
        final QBUser qbUser = new QBUser(user.getId(), user.getId());
        qbUser.setFullName(user.getFirstName() + " " + user.getLastName());
        if(user.getQuickBloxId()==null){
            Log.i("QB", "null");
            QBUsers.signUp(qbUser, new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {

                    user.setQuickBloxId(qbUser.getId().toString());
                }

                @Override
                public void onError(QBResponseException e) {

                }
            });
        }
        else{
            //login
        }
    }

    public void getDialogs(final CustomCallback cb) {
        QBRequestGetBuilder requestGetBuilder = new QBRequestGetBuilder();
        requestGetBuilder.setLimit(100);
        getChatService().getChatDialogs(QBDialogType.PRIVATE, requestGetBuilder, new QBEntityCallback<ArrayList<QBDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBDialog> qbDialogs, Bundle bundle) {
                for (final QBDialog d : qbDialogs) {
                    ChatDialogViewModel chatDialogViewModel = new ChatDialogViewModel();
                    chatDialogViewModel.setName(d.getName());
                    chatDialogViewModel.setLastText(d.getLastMessage());
                    DateTime dt = new DateTime();
                    chatDialogViewModel.setDate(dt.toString(DateTimeFormat.forPattern("dd MMM yyyy")));
                    chatDialogViewModel.setId(d.getDialogId());
                    dialogs.add(chatDialogViewModel);
                }
                Log.i("Chat Dialogs","Loaded");
                if(cb!=null){
                    cb.onCallback();
                }
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("Chat Dialogs",e.getMessage());
                if(cb!=null){
                    cb.onCallback();
                }
            }

        });
    }
}

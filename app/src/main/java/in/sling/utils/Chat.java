package in.sling.utils;

import android.os.Bundle;
import android.util.Log;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import in.sling.models.UserPopulated;

/**
 * Created by nitiv on 5/20/2016.
 */
public class Chat {

    private static Chat chatInstance;
    QBChatService chatService = QBChatService.getInstance();
    UserPopulated slingUser;
    QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();


    public static void initialize(UserPopulated user){
        chatInstance = new Chat(user);
    }
    public static Chat getChatInstance(){
        return chatInstance;
    }

    private Chat(UserPopulated user){
        slingUser = user;
        requestBuilder.setLimit(100);
       // chatSignUpOrIn(slingUser);
    }

    public void createSession(){
        final QBUser user = new QBUser("garrysantos", "garrysantospass");
        QBAuth.createSession(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(final QBSession session, Bundle params) {
                // success, login to chat
                QBUsers.signUp(user, new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(final QBUser user, Bundle args) {
                        Log.i("QB","Sign up "+ user.getId() );
                    }

                    @Override
                    public void onError(QBResponseException error) {
                        // error
                    }
                });


            }

            @Override
            public void onError(QBResponseException errors) {
                Log.e("Chat","Error " + errors.getMessage());
            }
        });
    }

    private void chatSignUpOrIn(final UserPopulated user){
        final QBUser qbUser = new QBUser(user.getId(), user.getId());
        qbUser.setFullName(user.getFirstName()+" "+user.getLastName());
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
}

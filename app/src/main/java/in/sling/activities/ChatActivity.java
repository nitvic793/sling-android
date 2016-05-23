package in.sling.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.quickblox.chat.QBPrivateChat;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBMessageListener;
import com.quickblox.chat.listeners.QBPrivateChatManagerListener;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

import in.sling.R;
import in.sling.adapters.ChatArrayAdapter;
import in.sling.fragments.ChatMessagingFragment;
import in.sling.fragments.NoticeFragment;
import in.sling.models.ChatMessage;
import in.sling.services.CustomCallback;
import in.sling.services.DataService;
import in.sling.utils.Chat;


public class ChatActivity extends AppCompatActivity {
    Chat chat = Chat.getChatInstance();
    DataService dataService;

    QBMessageListener<QBPrivateChat> privateChatMessageListener = new QBMessageListener<QBPrivateChat>() {
        @Override
        public void processMessage(QBPrivateChat privateChat, final QBChatMessage chatMessage) {

        }

        @Override
        public void processError(QBPrivateChat privateChat, QBChatException error, QBChatMessage originMessage){

        }
    };

    QBPrivateChatManagerListener privateChatManagerListener = new QBPrivateChatManagerListener() {
        @Override
        public void chatCreated(final QBPrivateChat privateChat, final boolean createdLocally) {
            if(!createdLocally){
                privateChat.addMessageListener(privateChatMessageListener);
            }
        }
    };

    private String dialogId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        final ChatMessagingFragment chatMessagingFragment = ChatMessagingFragment.newInstance();
        chatMessagingFragment.setArguments(bundle);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        dataService = new DataService(getSharedPreferences("in.sling", Context.MODE_PRIVATE));
        String id = dataService.getUser().getId();
        Log.i("ID", id);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in chat");
        progressDialog.show();
        chat.loginChat(new CustomCallback() {
            @Override
            public void onCallback() {
                progressDialog.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.container_chat,
                                chatMessagingFragment).commit();
            }
        });
        getSupportActionBar().setTitle(bundle.getString("name"));

    }

}

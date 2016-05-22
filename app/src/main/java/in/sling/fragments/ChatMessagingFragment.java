package in.sling.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.quickblox.chat.QBPrivateChat;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBMessageListener;
import com.quickblox.chat.listeners.QBPrivateChatManagerListener;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.SmackException;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;

import in.sling.R;
import in.sling.activities.ChatActivity;
import in.sling.adapters.ChatArrayAdapter;
import in.sling.adapters.ChatUsersAdapter;
import in.sling.models.ChatMessage;
import in.sling.models.ChatUserViewModel;
import in.sling.models.UserPopulated;
import in.sling.services.DataService;
import in.sling.utils.Chat;

/**
 * Created by abhishek on 18/02/16 at 5:40 PM.
 */
public class ChatMessagingFragment extends Fragment {

    Chat chat = Chat.getChatInstance();
    DataService dataService;

    QBMessageListener<QBPrivateChat> privateChatMessageListener = new QBMessageListener<QBPrivateChat>() {
        @Override
        public void processMessage(QBPrivateChat privateChat, final QBChatMessage chatMessage) {
            chatArrayAdapter.add(new ChatMessage(false, chatMessage.getBody()));
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

    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = false;
    private QBUser opponent;
    private QBPrivateChat qbPrivateChat;
    private UserPopulated slingUser;
    public ChatMessagingFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ChatMessagingFragment newInstance() {
        ChatMessagingFragment fragment = new ChatMessagingFragment();
        return fragment;
    }

    private String dialogId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_message, container, false);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        buttonSend = (Button) view.findViewById(R.id.send);
        dialogId = getArguments().getString("dialog");
        listView = (ListView) view.findViewById(R.id.msgview);

        chatArrayAdapter = new ChatArrayAdapter(getActivity().getApplicationContext(), R.layout.right);
        listView.setAdapter(chatArrayAdapter);
        dataService = new DataService(getActivity().getSharedPreferences("in.sling", Context.MODE_PRIVATE));
        slingUser = dataService.getUser();
        chatText = (EditText) view.findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        QBRequestGetBuilder requestGetBuilder = new QBRequestGetBuilder();
        requestGetBuilder.setLimit(100);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Retrieving chats");
        progressDialog.show();
        if(dialogId!=null){
            chat.getChatService().getDialogMessages(new QBDialog(getArguments().getString("dialog")), requestGetBuilder, new QBEntityCallback<ArrayList<QBChatMessage>>() {
                @Override
                public void onSuccess(ArrayList<QBChatMessage> qbChatMessages, Bundle bundle) {
                    Log.i("Chat Messages", qbChatMessages.size() + "");
                    for(QBChatMessage msg: qbChatMessages){
                        Log.i("Chat Users",msg.getSenderId() + " " + chat.getCurrentUser().getId() + " "  + (msg.getSenderId().equals(chat.getCurrentUser().getId())));
                        ChatMessage chatMessage = new ChatMessage((msg.getSenderId().equals(chat.getCurrentUser().getId()))?true:false,msg.getBody());
                        chatArrayAdapter.add(chatMessage);
                    }
                    final QBPrivateChatManager qbPrivateChatManager = chat.getPrivateChatManager();
                    QBUsers.getUserByLogin(getArguments().getString("opponent"), new QBEntityCallback<QBUser>() {
                        @Override
                        public void onSuccess(QBUser qbUser, Bundle bundle) {
                            opponent = qbUser;
                            QBPrivateChat privateChat = qbPrivateChatManager.getChat(qbUser.getId());
                            if (privateChat == null)
                                privateChat = chat.getPrivateChatManager().createChat(qbUser.getId(), privateChatMessageListener);
                            qbPrivateChat = privateChat;
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(QBResponseException e) {
                            Log.e("Chat Messaging", e.getMessage());
                            progressDialog.dismiss();
                        }
                    });
                }

                @Override
                public void onError(QBResponseException e) {
                    Log.e("Chat Message Error", e.getMessage());
                    progressDialog.dismiss();
                }
            });
        }
        else{
            final QBPrivateChatManager qbPrivateChatManager = chat.getPrivateChatManager();
            QBUsers.getUserByLogin(getArguments().getString("opponent"), new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                    opponent = qbUser;
                    QBPrivateChat privateChat = qbPrivateChatManager.getChat(qbUser.getId());
                    if (privateChat == null)
                        privateChat = chat.getPrivateChatManager().createChat(qbUser.getId(), privateChatMessageListener);
                    qbPrivateChat = privateChat;
                    progressDialog.dismiss();
                }

                @Override
                public void onError(QBResponseException e) {
                    Log.e("Chat Messaging", e.getMessage());
                    progressDialog.dismiss();
                }
            });
        }

        return view;
    }

    private boolean sendChatMessage() {
        try{
            QBChatMessage chatMessage = new QBChatMessage();
            chatMessage.setBody(chatText.getText().toString());
            chatMessage.setRecipientId(opponent.getId());
            chatMessage.setSenderId(chat.getCurrentUser().getId());
            chatMessage.setProperty("save_to_history", "1");
            chatMessage.setProperty("date_Sent",new DateTime().getMillis()+"");
            qbPrivateChat.sendMessage(chatMessage);

            chatArrayAdapter.add(new ChatMessage(true, chatText.getText().toString()));
            chatText.setText("");
            return true;
        }catch(SmackException.NotConnectedException e){
            Toast.makeText(getContext(),"No internet",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
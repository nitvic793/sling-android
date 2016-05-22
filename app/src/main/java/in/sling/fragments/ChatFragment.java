package in.sling.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBPrivateChat;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBMessageListener;
import com.quickblox.chat.listeners.QBPrivateChatManagerListener;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestBuilder;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

import in.sling.R;
import in.sling.adapters.ChatPagerAdapter;
import in.sling.models.UserPopulated;
import in.sling.services.CustomCallback;
import in.sling.services.DataService;
import in.sling.utils.Chat;

/**
 * Created by abhishek on 18/02/16 at 4:34 PM.
 */
public class ChatFragment extends Fragment {

    Chat chat = Chat.getChatInstance();
    DataService dataService;

    ArrayList<QBDialog> dialogs = new ArrayList<>();
    ArrayList<QBUser> chatUsers = new ArrayList<>();

    public static ChatFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }
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

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        dataService = new DataService(getActivity().getSharedPreferences("in.sling", Context.MODE_PRIVATE));
        View result = inflater.inflate(R.layout.fragment_chats, container, false);
        ViewPager pager = (ViewPager) result.findViewById(R.id.pager);
        result.setBackgroundColor(getResources().getColor(android.R.color.white));

        pager.setAdapter(new ChatPagerAdapter(getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout) result.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        //progressDialog.show();
//        chat.createSession(new CustomCallback() {
//            @Override
//            public void onCallback() {
//                final QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
//                requestBuilder.setLimit(100);
//                chat.getChatService().getChatDialogs(null, requestBuilder, new QBEntityCallback<ArrayList<QBDialog>>() {
//                    @Override
//                    public void onSuccess(ArrayList<QBDialog> qbDialogs, Bundle bundle) {
//                        Log.i("Dialogs", qbDialogs.size() + " ");
//                        chat.getChatService().getDialogMessages(qbDialogs.get(0), requestBuilder, new QBEntityCallback<ArrayList<QBChatMessage>>() {
//                            @Override
//                            public void onSuccess(ArrayList<QBChatMessage> qbChatMessages, Bundle bundle) {
//                                Log.i("Chats", qbChatMessages.size() + "");
//                                progressDialog.dismiss();
//                            }
//
//                            @Override
//                            public void onError(QBResponseException e) {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(QBResponseException e) {
//
//                    }
//                });

//                chat.loginChat(new CustomCallback() {
//                    @Override
//                    public void onCallback() {
//                        try {
//                            QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
//                            requestBuilder.setLimit(100);
//                            QBUser qbOpponent = chat.getQbUser(dataService.getParentsTeacherView().get(1).getId());
//
//                            int opponentId = qbOpponent.getId();
//                            chat.getChatService().getPrivateChatManager().addPrivateChatManagerListener(privateChatManagerListener);
//                            QBPrivateChat privateChat = chat.getPrivateChatManager().getChat(opponentId);
//                            if (privateChat == null)
//                                privateChat = chat.getPrivateChatManager().createChat(opponentId, privateChatMessageListener);
//                            QBChatMessage chatMessage = new QBChatMessage();
//                            chatMessage.setBody("Hi there!");
//                            chatMessage.setProperty("save_to_history", "1");
//                            privateChat.sendMessage(chatMessage);
//                            Log.i("Chat", "Sent!");
//
//                        }
//                        catch (Exception e)
//                        {
//                            Log.e("Chat Error", e.getMessage());
//                        }
//                    }
//                });
//            }
//        });
        return (result);
    }

    private void loadDialogs(){

    }

    private void loadChatUsers(){

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

package in.sling.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.quickblox.chat.model.QBDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import in.sling.R;
import in.sling.adapters.ChatDialogsAdapter;
import in.sling.models.ChatDialogViewModel;
import in.sling.services.CustomCallback;
import in.sling.services.DataService;
import in.sling.utils.Chat;
import in.sling.utils.DividerItemDecoration;

/**
 * Created by abhishek on 18/02/16 at 5:40 PM.
 */
public class ChatDialogsFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static ChatDialogsAdapter adapter;
    ArrayList<ChatDialogViewModel> dialogs = new ArrayList<>();
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    Chat chat = Chat.getChatInstance();
    DataService dataService;

    public ChatDialogsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ChatDialogsFragment newInstance(int sectionNumber) {
        ChatDialogsFragment fragment = new ChatDialogsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    boolean threadDone = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_dialog_recycler, container, false);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));

        recyclerView = (RecyclerView) view.findViewById(R.id.chat_dialog_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("Loading");
//        progressDialog.show();
        getDialogs();
//
//        chat.createSession(new CustomCallback() {
//            @Override
//            public void onCallback() {
//                progressDialog.dismiss();
//                getDialogs();
//            }
//        });

        return view;
    }

    private void refreshDialogs(final CustomCallback cb){
        QBRequestGetBuilder requestGetBuilder = new QBRequestGetBuilder();
        requestGetBuilder.setLimit(100);
        chat.getChatService().getChatDialogs(QBDialogType.PRIVATE, requestGetBuilder, new QBEntityCallback<ArrayList<QBDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBDialog> qbDialogs, Bundle bundle) {
                for(final QBDialog d: qbDialogs){
                    ChatDialogViewModel chatDialogViewModel = new ChatDialogViewModel();
                    chatDialogViewModel.setName(d.getName());
                    chatDialogViewModel.setLastText(d.getLastMessage());
                    DateTime dt = new DateTime();
                    chatDialogViewModel.setDate(dt.toString(DateTimeFormat.forPattern("dd MMM yyyy")));
                    chatDialogViewModel.setId(d.getDialogId());
                    dialogs.add(chatDialogViewModel);

                }
                adapter = new ChatDialogsAdapter(dialogs, getContext());
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                cb.onCallback();
            }

            @Override
            public void onError(QBResponseException e) {
                Toast.makeText(getActivity(),"Check internet connection",Toast.LENGTH_SHORT);
                cb.onCallback();
            }
        });
    }

    private void getDialogs() {
        QBRequestGetBuilder requestGetBuilder = new QBRequestGetBuilder();
        requestGetBuilder.setLimit(100);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Checking for new data...");
        progressDialog.show();
        chat.getChatService().getChatDialogs(QBDialogType.PRIVATE, requestGetBuilder, new QBEntityCallback<ArrayList<QBDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBDialog> qbDialogs, Bundle bundle) {
                for(final QBDialog d: qbDialogs){
                    ChatDialogViewModel chatDialogViewModel = new ChatDialogViewModel();
                    chatDialogViewModel.setName(d.getName());
                    chatDialogViewModel.setLastText(d.getLastMessage());
                    DateTime dt = new DateTime();
                    chatDialogViewModel.setDate(dt.toString(DateTimeFormat.forPattern("dd MMM yyyy")));
                    chatDialogViewModel.setId(d.getDialogId());
                    dialogs.add(chatDialogViewModel);

                }
                adapter = new ChatDialogsAdapter(dialogs, getContext());
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onError(QBResponseException e) {
                progressDialog.dismiss();
            }

            });
    }
}
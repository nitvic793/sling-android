package in.sling.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

import in.sling.R;
import in.sling.activities.ChatActivity;
import in.sling.adapters.ChatDialogsAdapter;
import in.sling.adapters.ChatUsersAdapter;
import in.sling.models.ChatDialogViewModel;
import in.sling.models.ChatUserViewModel;
import in.sling.models.User;
import in.sling.models.UserPopulated;
import in.sling.services.DataService;
import in.sling.utils.Chat;

/**
 * Created by abhishek on 18/02/16 at 5:40 PM.
 */
public class ChatUsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static ChatUsersAdapter adapter;
    ArrayList<ChatUserViewModel> users = new ArrayList<>();
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    Chat chat = Chat.getChatInstance();
    DataService dataService = DataService.getInstance();
    ArrayList<QBUser> chatUsers = new ArrayList<>();

    public ChatUsersFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ChatUsersFragment newInstance(int sectionNumber) {
        ChatUsersFragment fragment = new ChatUsersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_users_recycler, container, false);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        recyclerView = (RecyclerView) view.findViewById(R.id.chat_users_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadUsers();
        adapter = new ChatUsersAdapter(users, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void loadUsers(){
        if(dataService.getUserType().equalsIgnoreCase("parent")){
           ArrayList<User> teachers = new ArrayList<>(dataService.getTeachers());
            for(User t:teachers){
                ChatUserViewModel chatUserViewModel = new ChatUserViewModel();
                chatUserViewModel.setName(t.getFirstName() + " " + t.getLastName());
                chatUserViewModel.setId(t.getId());
                users.add(chatUserViewModel);
            }
        }
        else if(dataService.getUserType().equalsIgnoreCase("teacher")){
            ArrayList<UserPopulated> parents = new ArrayList<>(dataService.getParentsTeacherView());
            for(UserPopulated p: parents){
                ChatUserViewModel chatUserViewModel = new ChatUserViewModel();
                chatUserViewModel.setName(p.getFirstName() + " " + p.getLastName());
                chatUserViewModel.setId(p.getId());
                users.add(chatUserViewModel);
            }
        }
    }

    public class ChatUserOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity().getApplicationContext(), ChatActivity.class);
            startActivity(intent);
        }
    }
}
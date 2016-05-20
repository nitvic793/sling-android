package in.sling.fragments;

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
import com.quickblox.users.model.QBUser;

import in.sling.R;
import in.sling.adapters.ChatPagerAdapter;
import in.sling.models.UserPopulated;
import in.sling.services.DataService;
import in.sling.utils.Chat;

/**
 * Created by abhishek on 18/02/16 at 4:34 PM.
 */
public class ChatFragment extends Fragment {

    Chat chat = Chat.getChatInstance();
    DataService dataService;
    public static ChatFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        chat.createSession();
        return (result);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

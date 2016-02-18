package in.sling.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.sling.R;
import in.sling.adapters.ChatPagerAdapter;

/**
 * Created by abhishek on 18/02/16 at 4:34 PM.
 */
public class ChatFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_chats, container, false);
        ViewPager pager = (ViewPager) result.findViewById(R.id.pager);

        pager.setAdapter(new ChatPagerAdapter(getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout) result.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        return (result);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

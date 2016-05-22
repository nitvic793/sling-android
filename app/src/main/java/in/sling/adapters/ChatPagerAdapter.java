package in.sling.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.sling.fragments.ChatDialogsFragment;
import in.sling.fragments.ChatUsersFragment;
import in.sling.fragments.PlaceHolderFragment;

/**
 * Created by abhishek on 18/02/16 at 5:30 PM.
 */
public class ChatPagerAdapter extends FragmentPagerAdapter {

    public ChatPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return ChatDialogsFragment.newInstance(position);
        }
        else return ChatUsersFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "CHATS";
        } else {
            return "CONTACTS";
        }
    }
}

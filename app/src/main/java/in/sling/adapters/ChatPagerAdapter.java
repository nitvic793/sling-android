package in.sling.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
        return PlaceHolderFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "ALL";
        } else if (position == 1) {
            return "PARENT";
        } else {
            return "TEACHER";
        }
    }
}

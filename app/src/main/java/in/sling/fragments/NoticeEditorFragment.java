package in.sling.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.sling.R;

/**
 * Created by nitiv on 5/13/2016.
 */
public class NoticeEditorFragment extends android.support.v4.app.Fragment {

    public static NoticeEditorFragment newInstance(){
        return new NoticeEditorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notice_textedit, container, false);

        return rootView;
    }
}

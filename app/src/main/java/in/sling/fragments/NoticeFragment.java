package in.sling.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import in.sling.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by abhishek on 22/02/16 at 10:44 PM.
 */
public class NoticeFragment extends Fragment {

    private ImageView mNoticeImageView;
    private PhotoViewAttacher mAttacher;

    public static NoticeFragment newInstance() {

        Bundle args = new Bundle();

        NoticeFragment fragment = new NoticeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notice_board, container, false);
        mNoticeImageView = (ImageView) view.findViewById(R.id.notice_image);

        Drawable bitmap = getResources().getDrawable(R.drawable.sample_school_notice);
        mNoticeImageView.setImageDrawable(bitmap);

        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        mAttacher = new PhotoViewAttacher(mNoticeImageView);
        return view;
    }
}


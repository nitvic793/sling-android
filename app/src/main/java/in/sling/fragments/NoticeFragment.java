package in.sling.fragments;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import in.sling.R;
import in.sling.adapters.NoticeBoardAdapter;
import in.sling.models.NoticeBoardBase;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by abhishek on 22/02/16 at 10:44 PM.
 */
public class NoticeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;

    private static ArrayList<NoticeBoardBase> data;
    public static NoticeFragment newInstance() {

        Bundle args = new Bundle();

        NoticeFragment fragment = new NoticeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.notice_recycler, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<NoticeBoardBase>();
        NoticeBoardBase nb = new NoticeBoardBase();
        nb.setNotice("Test");
        nb.setId("1");
        data.add(nb);
        adapter = new NoticeBoardAdapter(data);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_notice_board, menu);
        MenuItem newNoticeMenu = menu.findItem(R.id.menu_new_notice);
        newNoticeMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               Toast.makeText(recyclerView.getContext(), "Test", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,
                                NoticeEditorFragment.newInstance()).commit();
                return false;
            }
        });
    }
}


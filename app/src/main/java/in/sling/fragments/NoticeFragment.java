package in.sling.fragments;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.List;

import in.sling.R;
import in.sling.adapters.NoticeBoardAdapter;
import in.sling.models.ClassRoom;
import in.sling.models.Data;
import in.sling.models.NoticeBoardBase;
import in.sling.models.NoticeBoardViewModel;
import in.sling.services.DataService;
import in.sling.services.RestFactory;
import in.sling.services.SlingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by abhishek on 22/02/16 at 10:44 PM.
 */
public class NoticeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    private SlingService service;
    private DataService dataService;

    private static ArrayList<NoticeBoardBase> data;
    private static ArrayList<NoticeBoardViewModel> noticeData = new ArrayList<>();

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
        SharedPreferences preferences = getActivity().getSharedPreferences("in.sling", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        service = RestFactory.createService(token);
        dataService = new DataService(preferences);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        data = new ArrayList<>(dataService.getNotices());
        View view = inflater.inflate(R.layout.notice_recycler, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        noticeData.clear();
       // dataService.LoadAllRequiredData(new ProgressDialog(getActivity().getApplicationContext()));
        loadNoticeBoardData();
        adapter = new NoticeBoardAdapter(noticeData);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void loadNoticeBoardData(){
        ArrayList<ClassRoom> classes = new ArrayList<>(dataService.getClasses());
        for(ClassRoom cls:classes){
            for(NoticeBoardBase nb: cls.getNotices()){
                NoticeBoardViewModel noticeVm = new NoticeBoardViewModel();
                noticeVm.setClassRoom(cls.getRoom() + " " + cls.getSubject());
                noticeVm.setNotice(nb.getNotice());
                noticeVm.setTeacher(cls.getTeacher().getFirstName() + " " + cls.getTeacher().getLastName());
                noticeVm.setId(nb.getId());
                noticeData.add(noticeVm);
            }
        }
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


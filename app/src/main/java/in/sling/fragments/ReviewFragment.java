package in.sling.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;

import in.sling.R;
import in.sling.adapters.NoticeBoardAdapter;
import in.sling.adapters.ReviewAdapter;
import in.sling.models.NoticeBoardBase;
import in.sling.models.Review;

/**
 * Created by abhishek on 18/02/16 at 6:18 PM.
 */
public class ReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;

    private static ArrayList<Review> data;

    public static ReviewFragment newInstance() {

        Bundle args = new Bundle();
        ReviewFragment fragment = new ReviewFragment();
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
        View view = inflater.inflate(R.layout.review_recycler, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.review_rv);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<>();
        Review r = new Review();
        r.setReview("Test Review");
        r.setId("1");
        data.add(r);
        adapter = new ReviewAdapter(data);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_student_review, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        MenuItem newReviewMenu = menu.findItem(R.id.menu_new_review);
        ArrayList<String> classList = new ArrayList<>();
        classList.add("Class V");
        classList.add("Class VI");
        classList.add("Class VII");

        ArrayAdapter<String> karant_adapter = new ArrayAdapter<>(((AppCompatActivity)getActivity()).getSupportActionBar().getThemedContext(), android.R.layout.simple_list_item_1, classList);

        spinner.setAdapter(karant_adapter);

        newReviewMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,
                               ReviewEditorFragment.newInstance()).commit();
                return true;
            }
        });
    }
}

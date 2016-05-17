package in.sling.fragments;

import android.content.Context;
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
import java.util.List;

import in.sling.R;
import in.sling.adapters.NoticeBoardAdapter;
import in.sling.adapters.ReviewAdapter;
import in.sling.models.ClassRoom;
import in.sling.models.ClassRoomNested;
import in.sling.models.NoticeBoardBase;
import in.sling.models.Review;
import in.sling.models.ReviewPopulated;
import in.sling.models.ReviewViewModel;
import in.sling.models.Student;
import in.sling.services.DataService;

/**
 * Created by abhishek on 18/02/16 at 6:18 PM.
 */
public class ReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    private DataService dataService;

    private static ArrayList<Review> data;
    private static ArrayList<ReviewViewModel> reviewData = new ArrayList<>();

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
        dataService = new DataService(getActivity().getSharedPreferences("in.sling", Context.MODE_PRIVATE));
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
        loadReviewData();
        adapter = new ReviewAdapter(reviewData);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void loadReviewData(){
        reviewData.clear();
        if(dataService.getUserType().equalsIgnoreCase("parent")){
            ArrayList<Student> wards = new ArrayList<>(dataService.getWardsParentView());
            for(Student ward: wards){
                for(Review review: ward.getReviews()){
                    ReviewViewModel reviewVm = new ReviewViewModel();
                    ClassRoom room = dataService.findClassRoom(review.getClassRoom());
                    reviewVm.setClassRoom(room.getRoom() + " " + room.getSubject());
                    reviewVm.setStudent(ward.getName());
                    reviewVm.setReview(review.getReview());
                    reviewData.add(reviewVm);
                }
            }
        }
        else if(dataService.getUserType().equalsIgnoreCase("teacher")){
            ArrayList<ReviewPopulated> reviews = new ArrayList<>(dataService.getReviewsTeacherView());
            for(ReviewPopulated review : reviews){
                ReviewViewModel reviewVm = new ReviewViewModel();
                reviewVm.setReview(review.getReview());
                reviewVm.setStudent(review.getStudent().getName());
                reviewVm.setClassRoom(review.getClassRoom().getRoom());
                reviewData.add(reviewVm);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_student_review, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        item.setVisible(false);
        MenuItem newReviewMenu = menu.findItem(R.id.menu_new_review);
        if(dataService.getUserType().equalsIgnoreCase("parent")){
            newReviewMenu.setVisible(false);
        }
        else{
            List<String> values = new ArrayList<String>();
            ArrayList<ClassRoom> classes = new ArrayList<>(dataService.getClasses());
            for(ClassRoom cl: classes){
                values.add(cl.getRoom() + " " + cl.getSubject());
            }

            ArrayAdapter<ClassRoom> karant_adapter = new ArrayAdapter<>(((AppCompatActivity)getActivity()).getSupportActionBar().getThemedContext(), android.R.layout.simple_list_item_1, classes);

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
}

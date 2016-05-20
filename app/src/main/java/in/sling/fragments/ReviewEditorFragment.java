package in.sling.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import in.sling.R;
import in.sling.models.ClassRoom;
import in.sling.models.Data;
import in.sling.models.Review;
import in.sling.models.ReviewPopulated;
import in.sling.models.Student;
import in.sling.models.StudentNested;
import in.sling.services.CustomCallback;
import in.sling.services.DataService;
import in.sling.services.SlingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nitiv on 5/13/2016.
 */
public class ReviewEditorFragment extends android.support.v4.app.Fragment {

    DataService dataService;
    Spinner classSpinner;
    EditText reviewText;
    Spinner studentSpinner;
    public static ReviewEditorFragment newInstance(){
        return new ReviewEditorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dataService = new DataService(getActivity().getSharedPreferences("in.sling", Context.MODE_PRIVATE));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.review_textedit, container, false);
        rootView.setBackgroundColor(getResources().getColor(android.R.color.white));

        getActivity().setTitle("New Notice");
        Button btn = (Button)rootView.findViewById(R.id.post_review_btn);
        reviewText = (EditText)rootView.findViewById(R.id.review_edit_text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Loading");
                Review review = new Review();
                review.setReview(reviewText.getText().toString());
                review.setStudent(((StudentNested) studentSpinner.getSelectedItem()).getId());
                review.setClassRoom(((ClassRoom) classSpinner.getSelectedItem()).getId());
                review.setTeacher(((ClassRoom) classSpinner.getSelectedItem()).getTeacher().getId());
                SlingService api = dataService.getAPIService();
                api.createReview(review).enqueue(new Callback<Data<Review>>() {
                    @Override
                    public void onResponse(Call<Data<Review>> call, Response<Data<Review>> response) {
                        dataService.LoadAllRequiredData(new CustomCallback() {
                            @Override
                            public void onCallback() {
                                Log.i("Check", "Done post");
                                progressDialog.dismiss();
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.container,
                                                ReviewFragment.newInstance()).commit();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Data<Review>> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("Error",t.getMessage());
                    }
                });
            }
        });

        ArrayList<StudentNested> students = new ArrayList<>(dataService.getStudentsTeacherView());
        Spinner studentSpinner = (Spinner)rootView.findViewById(R.id.review_student_spinner);
        ArrayAdapter<StudentNested> adapter = new ArrayAdapter<StudentNested>(getActivity().getApplicationContext(),R.layout.spinner_item, students);
        studentSpinner.setAdapter(adapter);
        this.studentSpinner = studentSpinner;
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_student_review, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        MenuItem newReviewMenu = menu.findItem(R.id.menu_new_review);
        newReviewMenu.setVisible(false);
        List<String> values = new ArrayList<String>();
        ArrayList<ClassRoom> classes = new ArrayList<>(dataService.getClasses());
        for(ClassRoom cl: classes){
            values.add(cl.getRoom() + " " + cl.getSubject());
        }
        ArrayAdapter<ClassRoom> karant_adapter = new ArrayAdapter<>(((AppCompatActivity)getActivity()).getSupportActionBar().getThemedContext(), android.R.layout.simple_list_item_1, classes);
        spinner.setAdapter(karant_adapter);
        classSpinner = spinner;
    }
}

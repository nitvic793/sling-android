package in.sling.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by abhishek on 18/02/16 at 6:18 PM.
 */
public class ReviewFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_student_review, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayList<String> classList = new ArrayList<>();
        classList.add("Class V");
        classList.add("Class VI");
        classList.add("Class VII");

        ArrayAdapter<String> karant_adapter = new ArrayAdapter<>(((AppCompatActivity)getActivity()).getSupportActionBar().getThemedContext(), android.R.layout.simple_list_item_1, classList);

        spinner.setAdapter(karant_adapter);
    }
}

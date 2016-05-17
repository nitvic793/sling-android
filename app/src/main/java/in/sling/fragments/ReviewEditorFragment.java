package in.sling.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import in.sling.R;

/**
 * Created by nitiv on 5/13/2016.
 */
public class ReviewEditorFragment extends android.support.v4.app.Fragment {

    public static ReviewEditorFragment newInstance(){
        return new ReviewEditorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.review_textedit, container, false);
        getActivity().setTitle("New Notice");
        Button btn = (Button)rootView.findViewById(R.id.post_review_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
            }
        });
        List<String> values = new ArrayList<String>();
        values.add("Test");
        values.add("Test2");
        Spinner classSpinner = (Spinner)rootView.findViewById(R.id.review_class_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, values);
        classSpinner.setAdapter(adapter);
        return rootView;
    }
}

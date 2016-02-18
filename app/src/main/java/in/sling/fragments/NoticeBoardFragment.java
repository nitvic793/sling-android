package in.sling.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import in.sling.R;
import in.sling.adapters.NoticeBoardSpinnerAdapter;

/**
 * Created by abhishek on 18/02/16 at 6:18 PM.
 */
public class NoticeBoardFragment extends Fragment {

    public static NoticeBoardFragment newInstance() {

        Bundle args = new Bundle();
        NoticeBoardFragment fragment = new NoticeBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_notice_board, container, false);

        Spinner spinner = (Spinner) result.findViewById(R.id.spinner);
        ArrayList<String> classList = new ArrayList<>();
        classList.add("Class V");
        classList.add("Class VI");
        classList.add("Class VII");

        spinner.setAdapter(new NoticeBoardSpinnerAdapter(getContext(), classList));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.notice_board_container, PlaceHolderFragment.newInstance(position + 1))
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return (result);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

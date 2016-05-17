package in.sling.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import in.sling.R;

/**
 * Created by nitiv on 5/13/2016.
 */
public class NoticeEditorFragment extends android.support.v4.app.Fragment {

    public static NoticeEditorFragment newInstance(){
        return new NoticeEditorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.notice_textedit, container, false);
        getActivity().setTitle("New Notice");
        Button btn = (Button)rootView.findViewById(R.id.post_notice_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Data", "Test");
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,
                                NoticeFragment.newInstance()).commit();
                Toast.makeText(rootView.getContext(),"Pop Back", Toast.LENGTH_SHORT);
            }
        });
        List<String> values = new ArrayList<String>();
        values.add("Test");
        values.add("Test2");
        Spinner classSpinner = (Spinner)rootView.findViewById(R.id.notice_class_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, values);
        classSpinner.setAdapter(adapter);
        return rootView;
    }
}
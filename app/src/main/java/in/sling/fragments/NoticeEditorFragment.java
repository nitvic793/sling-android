package in.sling.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import in.sling.R;
import in.sling.models.ClassRoom;
import in.sling.models.ClassRoomNested;
import in.sling.models.Data;
import in.sling.models.NoticeBoard;
import in.sling.models.NoticeBoardBase;
import in.sling.services.CustomCallback;
import in.sling.services.DataService;
import in.sling.services.SlingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nitiv on 5/13/2016.
 */
public class NoticeEditorFragment extends android.support.v4.app.Fragment {

    private DataService dataService;

    private EditText noticeText;
    private Spinner classSpinner;

    public static NoticeEditorFragment newInstance(){
        return new NoticeEditorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dataService = new DataService(getActivity().getSharedPreferences("in.sling", Context.MODE_PRIVATE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.notice_textedit, container, false);
        rootView.setBackgroundColor(getResources().getColor(android.R.color.white));

        getActivity().setTitle("New Notice");
        Button btn = (Button)rootView.findViewById(R.id.post_notice_btn);
        noticeText = (EditText)rootView.findViewById(R.id.notice_edittext);
        List<String> values = new ArrayList<String>();
        ArrayList<ClassRoom> classes = new ArrayList<>(dataService.getClasses());
        for(ClassRoom cl: classes){
            values.add(cl.getRoom() + " " + cl.getSubject());
        }
        final Spinner classSpinner = (Spinner)rootView.findViewById(R.id.notice_class_spinner);
        ArrayAdapter<ClassRoom> adapter = new ArrayAdapter<ClassRoom>(getActivity().getApplicationContext(),R.layout.spinner_item, classes);
        classSpinner.setAdapter(adapter);
        this.classSpinner = classSpinner;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // final ProgressDialog progress = new ProgressDialog(getActivity());
               // progress.setTitle("Posting");
               // progress.setMessage("Creating new notice...");
                Log.e("Data", "Test");
                SlingService api = dataService.getAPIService();
                String notice = noticeText.getText().toString();
                ClassRoom cls = (ClassRoom)classSpinner.getSelectedItem();
                NoticeBoardBase noticeBoard = new NoticeBoardBase();
                noticeBoard.setNotice(notice);
                noticeBoard.setClassRoom(cls.getNested().getId());
                api.createNotice(noticeBoard).enqueue(new Callback<Data<NoticeBoardBase>>() {
                    @Override
                    public void onResponse(Call<Data<NoticeBoardBase>> call, Response<Data<NoticeBoardBase>> response) {
                       // progress.dismiss();
                       // progress.setTitle("Loading");
                      //  progress.setMessage("Refreshing Data...");
                        dataService.LoadAllRequiredData(new CustomCallback() {
                            @Override
                            public void onCallback() {
                                // progress.dismiss();
                                Log.i("Check", "Done");
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.container,
                                                NoticeFragment.newInstance()).commit();
                                Toast.makeText(rootView.getContext(), "Done", Toast.LENGTH_SHORT);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Data<NoticeBoardBase>> call, Throwable t) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: Could not post notice!", Toast.LENGTH_SHORT);
                       // progress.dismiss();
                    }
                });

            }
        });
        return rootView;
    }


}

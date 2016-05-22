package in.sling.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;

import in.sling.R;
import in.sling.activities.ChatActivity;
import in.sling.models.ChatDialogViewModel;
import in.sling.models.ReviewViewModel;
import in.sling.models.User;
import in.sling.models.UserPopulated;
import in.sling.services.DataService;

/**
 * Created by nitiv on 5/13/2016.
 */
public class ChatDialogsAdapter extends RecyclerView.Adapter<ChatDialogsAdapter.MyViewHolder> {
    private ArrayList<ChatDialogViewModel> dataSet;
    Context context;
    DataService dataService;
    public static class MyViewHolder extends ViewHolder {


        TextView textViewName;
        TextView textViewVersion;
        TextView textDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.chat_dialog_head);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.chat_dialog_text);
            textDate = (TextView) itemView.findViewById(R.id.chat_dialog_date);

            // this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_chat_dialogs, parent, false);

            // view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        dataService = new DataService(context.getSharedPreferences("in.sling",Context.MODE_PRIVATE));
        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        String name = "";
        if(dataService.getUserType().equalsIgnoreCase("parent")){
            User u = dataService.findTeacher(dataSet.get(position).getName());
            name = u.getFirstName() + " " + u.getLastName();
        }
        else if(dataService.getUserType().equalsIgnoreCase("teacher")){
            UserPopulated u = dataService.findParent(dataSet.get(position).getName());
            name = u.getFirstName() + " " + u.getLastName();
        }
        textViewName.setText(name);
        textViewVersion.setText(dataSet.get(position).getLastText());
        //DateTime dt = new DateTime(dataSet.get(position).getDate());
        //holder.textDate.setText(dt.toLocalDate().toString(DateTimeFormat.forPattern("dd MMM yyyy")));
        holder.textDate.setText(dataSet.get(position).getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("dialog", dataSet.get(position).getId());
                bundle.putString("opponent", dataSet.get(position).getName());
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });

    }


        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    public ChatDialogsAdapter(ArrayList<ChatDialogViewModel> data, Context context) {
        this.context = context;
        this.dataSet = data;
    }
    }






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
import in.sling.fragments.ChatUsersFragment;
import in.sling.models.ChatUserViewModel;
import in.sling.models.ReviewViewModel;

/**
 * Created by nitiv on 5/13/2016.
 */
public class ChatUsersAdapter extends RecyclerView.Adapter<ChatUsersAdapter.MyViewHolder> {
    private ArrayList<ChatUserViewModel> dataSet;
    Context context;
    public static class MyViewHolder extends ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        TextView textDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.chat_user_name);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.review_text);
            textDate = (TextView) itemView.findViewById(R.id.review_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            // this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_chat_users, parent, false);

            // view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        textViewName.setText(dataSet.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("opponent",dataSet.get(position).getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
//        textViewVersion.setText(dataSet.get(position).getReview());
//        DateTime dt = new DateTime(dataSet.get(position).getCreatedAt());
//        holder.textDate.setText(dt.toLocalDate().toString(DateTimeFormat.forPattern("dd MMM yyyy")));
    }


        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    public ChatUsersAdapter(ArrayList<ChatUserViewModel> data, Context context) {
        this.dataSet = data;
        this.context = context;

    }
    }






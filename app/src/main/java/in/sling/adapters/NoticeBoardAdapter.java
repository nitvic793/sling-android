package in.sling.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.sling.R;
import in.sling.models.NoticeBoard;
import in.sling.models.NoticeBoardBase;

/**
 * Created by nitiv on 5/13/2016.
 */
public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.MyViewHolder> {
    private ArrayList<NoticeBoardBase> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.teacherName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.noticeText);
            // this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_notice_board, parent, false);

            // view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(position).getId());
        textViewVersion.setText(dataSet.get(position).getNotice());
    }


        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    public NoticeBoardAdapter(ArrayList<NoticeBoardBase> data) {
        this.dataSet = data;
    }
    }






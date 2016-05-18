package in.sling.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;

import in.sling.R;
import in.sling.models.NoticeBoard;
import in.sling.models.NoticeBoardBase;
import in.sling.models.NoticeBoardViewModel;
import in.sling.services.DataService;

/**
 * Created by nitiv on 5/13/2016.
 */
public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.MyViewHolder> {
    private ArrayList<NoticeBoardViewModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;
        TextView textDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.teacherName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.noticeText);
            textDate = (TextView) itemView.findViewById(R.id.notice_date);
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
        DateTime dt = new DateTime(dataSet.get(position).getCreatedAt());
        holder.textDate.setText(dt.toLocalDate().toString(DateTimeFormat.forPattern("dd MMM yyyy")));
        textViewName.setText(dataSet.get(position).getClassRoom());
        textViewVersion.setText(dataSet.get(position).getNotice());
    }


        @Override
        public int getItemCount() {
            return dataSet.size();
        }

    public NoticeBoardAdapter(ArrayList<NoticeBoardViewModel> data) {
        this.dataSet = data;
    }
}






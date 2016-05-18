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
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import in.sling.R;
import in.sling.models.NoticeBoardBase;
import in.sling.models.Review;
import in.sling.models.ReviewViewModel;

/**
 * Created by nitiv on 5/13/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    private ArrayList<ReviewViewModel> dataSet;

    public static class MyViewHolder extends ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        TextView textDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.review_name);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.review_text);
            textDate = (TextView) itemView.findViewById(R.id.review_date);
            // this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_review, parent, false);

            // view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        textViewName.setText(dataSet.get(position).getStudent() + "(" + dataSet.get(position).getClassRoom() + ")");
        textViewVersion.setText(dataSet.get(position).getReview());
        DateTime dt = new DateTime(dataSet.get(position).getCreatedAt());
        holder.textDate.setText(dt.toLocalDate().toString(DateTimeFormat.forPattern("dd MMM yyyy")));
    }


        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    public ReviewAdapter(ArrayList<ReviewViewModel> data) {
        this.dataSet = data;
    }
    }






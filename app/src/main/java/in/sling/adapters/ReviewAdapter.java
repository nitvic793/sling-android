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
import in.sling.models.NoticeBoardBase;
import in.sling.models.Review;

/**
 * Created by nitiv on 5/13/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    private ArrayList<Review> dataSet;

    public static class MyViewHolder extends ViewHolder {

        TextView textViewName;
        TextView textViewVersion;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.review_name);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.review_text);
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
        textViewName.setText(dataSet.get(position).getId());
        textViewVersion.setText(dataSet.get(position).getReview());
    }


        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    public ReviewAdapter(ArrayList<Review> data) {
        this.dataSet = data;
    }
    }






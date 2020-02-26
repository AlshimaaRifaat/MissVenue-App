package com.ibsvalleyn.missvenue.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.models.ReviewModel;

import java.util.List;


public class AdsCommentsAdapter extends RecyclerView.Adapter<AdsCommentsAdapter.AdsHolder> {

    private List<ReviewModel.Review_Lists> modelList;
    private Context context;

    public AdsCommentsAdapter(List<ReviewModel.Review_Lists> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }


    @NonNull
    @Override
    public AdsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comments, parent, false);
        return new AdsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsHolder holder, int i) {

        holder.comments_tvUserName.setText(modelList.get(i).getCustomerName()+": ");
        holder.comments_tvComment.setText(modelList.get(i).getReview());
        holder.infow_window_rating.setRating(modelList.get(i).getRate());

    }

    @Override
    public int getItemCount() {
        return modelList != null ? modelList.size() : 0;
    }

    class AdsHolder extends RecyclerView.ViewHolder {

        private TextView comments_tvUserName,comments_tvComment;
        SimpleRatingBar infow_window_rating;
        Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ubuntu-R.ttf");

        AdsHolder(@NonNull View itemView) {
            super(itemView);
            comments_tvUserName = itemView.findViewById(R.id.comments_tvUserName);
            comments_tvComment = itemView.findViewById(R.id.comments_tvComment);
            infow_window_rating = itemView.findViewById(R.id.infow_window_rating);

            comments_tvUserName.setTypeface(customTypeOne);
            comments_tvComment.setTypeface(customTypeOne);

        }
    }
}

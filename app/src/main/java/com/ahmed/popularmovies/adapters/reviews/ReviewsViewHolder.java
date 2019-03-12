package com.ahmed.popularmovies.adapters.reviews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.popularmovies.R;
import com.ahmed.popularmovies.models.MovieReviewItem;

public class ReviewsViewHolder extends RecyclerView.ViewHolder{

    private TextView tv_Movie_Review;
    private ImageView iv_Movie_Review_separator;


    public ReviewsViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setTag(this);
        View view = itemView.findViewById(R.id.view_review_row);
        tv_Movie_Review = view.findViewById(R.id.tv_movie_review);
        iv_Movie_Review_separator = view.findViewById(R.id.iv_movie_review_separator);
    }

    public void bindData(MovieReviewItem movieReviewItem){
        tv_Movie_Review.setText(movieReviewItem.getReviewContent());
    }
}

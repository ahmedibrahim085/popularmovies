package com.ahmed.popularmovies.adapters.reviews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.popularmovies.R;
import com.ahmed.popularmovies.models.MovieReviewItem;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {

    private List<MovieReviewItem> movieReviewItemList;

    public ReviewsAdapter(List<MovieReviewItem> reviewItems) {
        this.movieReviewItemList = reviewItems;

    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // view holder is abstract
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .review_recycler_view, viewGroup, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder reviewsViewHolder, int i) {
        reviewsViewHolder.bindData(movieReviewItemList.get(i));

    }

    @Override
    public int getItemCount() {
        return movieReviewItemList.size();
    }

    public void updateList(List<MovieReviewItem> movieReviewItems) {
        this.movieReviewItemList = movieReviewItems;
        notifyDataSetChanged();
    }
}

package com.ahmed.popularmovies.adapters.trailers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.popularmovies.R;
import com.ahmed.popularmovies.models.TrailerItem;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder>{

    private View.OnClickListener onItemClickListener;

    private  List<TrailerItem> trailerItems;


    public TrailerAdapter(List<TrailerItem> trailerItems) {
        this.trailerItems = trailerItems;

    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // view holder is abstract
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .trailer_recycler_view, viewGroup, false);
        return new TrailerViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int position) {
        trailerViewHolder.bindData(trailerItems.get(position));

    }

    @Override
    public int getItemCount() {
        return trailerItems.size();
    }

    public void updateList(List<TrailerItem> trailer) {
        trailerItems.clear();
        trailerItems.addAll(trailer);
        notifyDataSetChanged();
    }

    public TrailerItem getMovieItems(int position) {
        return trailerItems.get(position);
    }

    public void setItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }

}

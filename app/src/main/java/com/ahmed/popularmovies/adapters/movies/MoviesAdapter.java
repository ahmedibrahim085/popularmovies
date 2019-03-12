package com.ahmed.popularmovies.adapters.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.popularmovies.R;
import com.ahmed.popularmovies.models.MovieItem;

import java.util.List;

// Should handle inflated views & keep track of our data
public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private  List<MovieItem> movieItems;

    private View.OnClickListener onItemClickListener;

    public MoviesAdapter(List<MovieItem> movieItems) {
        this.movieItems = movieItems;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // view holder is abstract
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .view_recyclerview_row, parent, false);
        return new MoviesViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int position) {
        moviesViewHolder.bindData(movieItems.get(position));
    }

    @Override // return the total numbers of view holders that will be needed to display holders
    public int getItemCount() {
        return movieItems.size();
    }

    public void updateList(List<MovieItem> movieItem) {
        if (movieItem !=null && movieItem.size()>0) {
            movieItems.clear();
            movieItems.addAll(movieItem);
            notifyDataSetChanged();
        }
    }

    public MovieItem getMovieItems(int position) {
        return movieItems.get(position);
    }

    public void setItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }

    public List<MovieItem> getMoviesList(){

        return movieItems;
    }
}

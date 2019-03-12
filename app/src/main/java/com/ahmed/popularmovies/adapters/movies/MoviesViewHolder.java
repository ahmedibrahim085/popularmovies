package com.ahmed.popularmovies.adapters.movies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ahmed.popularmovies.R;
import com.ahmed.popularmovies.models.MovieItem;
import com.ahmed.popularmovies.utils.Networks;
import com.squareup.picasso.Picasso;


  class MoviesViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = MoviesViewHolder.class.getSimpleName();


    private ImageView iv_recycler;

      public MoviesViewHolder(View view, View.OnClickListener onItemClickListener) {
          super(view);
          itemView.setTag(this);
          itemView.setOnClickListener(onItemClickListener);
          iv_recycler = itemView.findViewById(R.id.iv_movie_poster);      }

      public void bindData(MovieItem movieItem){
        Picasso.get().load(Networks.getsPosterPathBuilt(movieItem.getPosterPath())).into
                (iv_recycler);
    }


}

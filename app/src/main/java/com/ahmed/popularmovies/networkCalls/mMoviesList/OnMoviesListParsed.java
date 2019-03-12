package com.ahmed.popularmovies.networkCalls.mMoviesList;

import com.ahmed.popularmovies.models.MovieItem;

import java.util.List;

public interface OnMoviesListParsed {

    void moviesListParsed(List<MovieItem> movieItem);

    void onMoviesListParsedError(String error);

}

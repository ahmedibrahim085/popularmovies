package com.ahmed.popularmovies.networkCalls.movieDetails;

import com.ahmed.popularmovies.models.MovieItem;

public interface OnMovieDetailsParsed {

    void movieDetailsParsed(MovieItem movieItem);

    void onMovieDetailsParsedError(String error);

}

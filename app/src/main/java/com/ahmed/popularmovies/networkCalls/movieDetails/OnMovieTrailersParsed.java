package com.ahmed.popularmovies.networkCalls.movieDetails;

import com.ahmed.popularmovies.models.TrailerItem;

import java.util.List;

public interface OnMovieTrailersParsed {

    void movieTrailersParsed(List<TrailerItem> trailerItemList);

    void onMovieTrailersParsedError(String error);

}

package com.ahmed.popularmovies.networkCalls.movieDetails;

import com.ahmed.popularmovies.models.MovieReviewItem;

import java.util.List;

public interface OnMovieReviewsParsed {

    void movieReviewsParsed(List<MovieReviewItem> movieReviewItems);

    void onMovieReviewParsedError(String error);

}

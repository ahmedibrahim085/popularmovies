package com.ahmed.popularmovies.models;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MovieItem implements Serializable {

    private static final String TAG = MovieItem.class.getSimpleName();

    // List of Keys of the JSON
    private static final String MOVIE_VOTE_COUNT = "vote_count";
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_VIDEO = "video";
    private static final String MOVIE_VOTE_AVERAGE = "vote_average";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_POPULARITY = "popularity";
    private static final String MOVIE_POSTER_PATH = "poster_path";
    private static final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
    private static final String MOVIE_ORIGINAL_TITLE = "original_title";
    private static final String MOVIE_BACKDROP_PATH = "backdrop_path";
    private static final String MOVIE_ADULT = "adult";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_RUN_TIME = "runtime";

    private String overview;
    private String originalLanguage;
    private String originalTitle;
    private boolean video;
    private String title;
    private String posterPath;
    private String backdropPath;
    private String releaseDate;
    private double voteAverage;
    private double popularity;
    private int id;
    private boolean adult;
    private int voteCount;
    private int runTime;

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isVideo() {
        return video;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "{" +
                        "\"overview = \"" + ":"+ "\"" +overview + "\"" +
                        ",\"original_language = \"" + ":"+ "\"" +originalLanguage + "\"" +
                        ",\"original_title = \"" + ":"+ "\"" +originalTitle + "\"" +
                        ",\"video = \"" + ":"+ "\"" +video + ":"+ "\"" +
                        ",\"title = \"" + ":"+ "\"" +title + ":"+ "\"" +
//                        ",genre_ids = \"" + genreIds + "\"" +
                        ",\"poster_path = \"" + ":"+ "\"" +posterPath + "\"" +
                        ",\"backdrop_path = \"" + ":"+ "\"" +backdropPath + "\"" +
                        ",\"release_date = \"" + ":"+ "\"" +releaseDate + "\"" +
                        ",\"vote_average = \"" + ":"+ "\"" +voteAverage + "\"" +
                        ",\"popularity = \"" + ":"+ "\"" +popularity + "\"" +
                        ",\"id = \"" + ":"+ "\"" +id + "\"" +
                        ",\"adult = \"" + ":"+ "\"" +adult + "\"" +
                        ",\"vote_count = \"" + ":"+ "\"" +voteCount + "\"" +
                        ",\"run_time = \"" + ":"+ "\"" +runTime + "\"" +
                        "}";
    }


    public static MovieItem getMovieItemFromJson(JSONObject movieItem) {
        MovieItem resultsItem = new MovieItem();
        try {
            resultsItem.setId(movieItem.getInt(MOVIE_ID));
            resultsItem.setVideo(movieItem.getBoolean(MOVIE_VIDEO));
            resultsItem.setVoteAverage(movieItem.getDouble(MOVIE_VOTE_AVERAGE));
            resultsItem.setTitle(movieItem.getString(MOVIE_TITLE));
            resultsItem.setPopularity(movieItem.getDouble(MOVIE_POPULARITY));
            resultsItem.setPosterPath(movieItem.getString(MOVIE_POSTER_PATH));
            resultsItem.setOriginalLanguage(movieItem.getString(MOVIE_ORIGINAL_LANGUAGE));
            resultsItem.setOriginalTitle(movieItem.getString(MOVIE_ORIGINAL_TITLE));
            resultsItem.setVoteCount(movieItem.getInt(MOVIE_VOTE_COUNT));
            if (movieItem.getInt(MOVIE_RUN_TIME) > 0) {
                resultsItem.setRunTime(movieItem.getInt(MOVIE_RUN_TIME));
            }
            resultsItem.setBackdropPath(movieItem.getString(MOVIE_BACKDROP_PATH));
            resultsItem.setAdult(movieItem.getBoolean(MOVIE_ADULT));
            resultsItem.setOverview(movieItem.getString(MOVIE_OVERVIEW));
            resultsItem.setReleaseDate(movieItem.getString(MOVIE_RELEASE_DATE));
//            Log.d(TAG, "\n getMovieItemFromJson -> " + resultsItem.toString());
            return resultsItem;
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.d(TAG, "onPostExecute -: getMovieItemFromJson AGAIN  ->" + resultsItem.toString());
        return resultsItem;
    }
}
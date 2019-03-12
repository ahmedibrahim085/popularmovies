package com.ahmed.popularmovies.models;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MovieReviewItem implements Serializable {

    private static final String TAG = MovieReviewItem.class.getSimpleName();
    // List of Keys of the Movie Review Item JSON
    private static final String MOVIE_REVIEW_AUTHOR = "author";
    private static final String MOVIE_REVIEW_CONTENT = "content";
    private static final String MOVIE_REVIEW_ID = "id";
    private static final String MOVIE_REVIEW_URL = "url";

    // List of VALUES of the Movie Review Item JSON
    private String author;
    private String content;
    private String id;
    private String url;


    // ======= Keys GETTERS ======= START =======
    public static String getMovieReviewAuthor() {
        return MOVIE_REVIEW_AUTHOR;
    }

    public static String getMovieReviewContent() {
        return MOVIE_REVIEW_CONTENT;
    }

    public static String getMovieReviewId() {
        return MOVIE_REVIEW_ID;
    }

    public static String getMovieReviewUrl() {
        return MOVIE_REVIEW_URL;
    }

    // ======= Keys GETTERS ======= END/FIN =======


    // ======= Keys GETTERS && SETTERS of the Movie Review Item JSON ======= START =======

    public String getReviewAuthor() {
        return this.author;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.author = reviewAuthor;
    }

    public String getReviewContent() {
        return content;
    }

    public void setReviewContent(String reviewContent) {
        this.content = reviewContent;
    }

    public String getReviewID() {
        return this.id;
    }

    public void setReviewID(String reviewID) {
        this.id = reviewID;
    }

    public String getReviewURL() {
        return this.url;
    }

    public void setReviewURL(String reviewURL) {
        this.url = reviewURL;
    }

    // ======= Keys GETTERS && SETTERS of the Movie Review Item JSON ======= START =======



    @NonNull
    @Override
    public String toString() {
        return
                "{" +
                        "\"author = \"" + ":"+ "\"" +author + "\"" +
                        ",\"content = \"" + ":"+ "\"" +content + "\"" +
                        ",\"id = \"" + ":"+ "\"" +id + "\"" +
                        ",\"url = \"" + ":"+ "\"" +url + ":"+ "\"" +
                        "" +
                        "}";
    }

    public static MovieReviewItem getMovieReviewItemFromJson(JSONObject movieReviewInfo) {
        MovieReviewItem movieReviewItem = new MovieReviewItem();
        try {
            movieReviewItem.setReviewID(movieReviewInfo.getString(MOVIE_REVIEW_ID));
            movieReviewItem.setReviewAuthor(movieReviewInfo.getString(MOVIE_REVIEW_AUTHOR));
            movieReviewItem.setReviewContent(movieReviewInfo.getString(MOVIE_REVIEW_CONTENT));
            movieReviewItem.setReviewURL(movieReviewInfo.getString(MOVIE_REVIEW_URL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onPostExecute -: getMovieReviewItemFromJson ->" + movieReviewItem.toString());
        return movieReviewItem;
    }
}

package com.ahmed.popularmovies.database;

/* as per Udacity
* 1- The titles and IDs of the user’s favorite movies are stored
* 2- Data is updated whenever the user favorites or unfavorites a movie.
* 3- When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the database.* /

/* Note I will add the Poster URL as well as I am trying to load the poster Images faster in Screen */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "favoriteMovies")
public class FavoriteMoviesEntry {

    @PrimaryKey
    private int movieID;
    @ColumnInfo(name = "movieTitle")
    private String movieTitle;
    @ColumnInfo(name = "posterURL")
    private String moviePosterURL;


    public FavoriteMoviesEntry(int movieID, String movieTitle, String moviePosterURL) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.moviePosterURL = moviePosterURL;
    }


    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMoviePosterURL() {
        return moviePosterURL;
    }

    public void setMoviePosterURL(String moviePosterURL) {
        this.moviePosterURL = moviePosterURL;
    }
}

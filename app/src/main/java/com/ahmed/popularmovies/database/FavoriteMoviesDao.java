package com.ahmed.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteMoviesDao {

    @Query("SELECT * From favoriteMovies ORDER BY movieID")
    LiveData<List<FavoriteMoviesEntry>> loadAllFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavoriteMovie(FavoriteMoviesEntry favoriteMovie);

    @Delete
    void removeFavoriteMovie(FavoriteMoviesEntry favoriteMovie);

    @Query("SELECT * From favoriteMovies WHERE movieID = :id")
    LiveData<List<FavoriteMoviesEntry>> getFavoriteMovie(int id);
}

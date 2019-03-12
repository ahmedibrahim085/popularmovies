package com.ahmed.popularmovies.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.ahmed.popularmovies.database.FavoriteMoviesDao;
import com.ahmed.popularmovies.database.FavoriteMoviesEntry;

@Database(
        entities = {FavoriteMoviesEntry.class},
        version = 1,
        exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favoriteMoviesList";
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null){
            synchronized (LOCK){
                Log.d(TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
//        Log.d(TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract FavoriteMoviesDao favoriteMoviesDao();


    // Before Applying Live Data
/*    private static List<FavoriteMoviesEntry> favoriteMoviesEntryList;

    public static List<FavoriteMoviesEntry> getFavoriteMoviesEntryList() {
        return favoriteMoviesEntryList;
    }

    public static void setFavoriteMoviesEntryList(List<FavoriteMoviesEntry> favoriteMoviesList) {
        favoriteMoviesEntryList = favoriteMoviesList;
    }

    public void LoadFavoriteMoviesList(){
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                setFavoriteMoviesEntryList(favoriteMoviesDao().loadAllFavoriteMovies());
            }
        });
    }*/
}

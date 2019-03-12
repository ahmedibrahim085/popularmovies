package com.ahmed.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ahmed.popularmovies.database.FavoriteMoviesEntry;
import com.ahmed.popularmovies.utils.AppDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<FavoriteMoviesEntry>> allFavoriteMovie;
    private static final String TAG = MainViewModel.class.getSimpleName();


    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the Favorite Movies from the Database");
        allFavoriteMovie = appDatabase.favoriteMoviesDao().loadAllFavoriteMovies();
    }

    public LiveData<List<FavoriteMoviesEntry>> getAllFavoriteMovie() {
        return allFavoriteMovie;
    }
}

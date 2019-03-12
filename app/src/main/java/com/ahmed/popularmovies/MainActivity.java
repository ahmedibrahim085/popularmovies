package com.ahmed.popularmovies;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ahmed.popularmovies.adapters.movies.MoviesAdapter;
import com.ahmed.popularmovies.database.FavoriteMoviesEntry;
import com.ahmed.popularmovies.models.MovieItem;
import com.ahmed.popularmovies.networkCalls.mMoviesList.GetMoviesListAsync;
import com.ahmed.popularmovies.networkCalls.mMoviesList.OnMoviesListParsed;
import com.ahmed.popularmovies.utils.AppDatabase;
import com.ahmed.popularmovies.utils.Constants;
import com.ahmed.popularmovies.utils.Networks;
import com.ahmed.popularmovies.utils.SharedPrefs;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMoviesListParsed {

    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    Networks networks;
    private MoviesAdapter adapter;
    ArrayList<MovieItem> moviesData;

    AppDatabase appDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networks = new Networks();
//        favoriteMoviesEntryList = new ArrayList<>();
        setContentView(R.layout.activity_main);
        initialLoadFavoriteMoviesList();
        initiateRecyclerView();
        changeActivityTitle();
        if (savedInstanceState!=null){
            moviesData =(ArrayList<MovieItem>) savedInstanceState.getSerializable("savedMovieItems");
            if (moviesData !=null) {
                adapter.updateList(moviesData);
            }else{
                getMovies();
            }
        }else{
            getMovies();
        }
    }

    private void initialLoadFavoriteMoviesList(){
        appDatabase = AppDatabase.getsInstance(this);
//        setupMainViewModelForFavoriteMovies();
//        appDatabase.LoadFavoriteMoviesList();
    }

    // ======= Change Activity Title to Match ======= START ========
    private void changeActivityTitle() {
        if (SharedPrefs.getSortOrder().equals(Constants.getSortOrderMostPopular())) {
            this.setTitle(Constants.getMenu_ITEM_MOST_POPULAR());
        } else if (SharedPrefs.getSortOrder().equals(Constants.getSortOrderHighestRated())){
            this.setTitle(Constants.getMenu_ITEM_HIGHEST_RATED());
        } else {
            this.setTitle(Constants.getMenu_ITEM_FAVORITE_MOVIES());
        }
    }
    // ======= Change Activity Title to Match ======= END /FIN ========

    // ======= Recycler VIEW ======= START ========
    private void initiateRecyclerView(){
        recyclerView = findViewById(R.id.main_recyclerView);
        // Add layout manager to manage layout
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        // Add adapter to handle data from data source to view
        adapter = new MoviesAdapter(new ArrayList());
        recyclerView.setAdapter(adapter);
        // to OPen the Movie Details when click on the item in the recycle view
        adapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                MovieItem movieItem = adapter.getMovieItems(position);
                showMovieDetails(movieItem);
            }
        });
    }

    // ======= Recycler VIEW ======= END /FIN ========

    // ======= ======= ======= ======= ======== ======= =======

    // ======= Menu ======= START ========
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        MenuItem viewSettingMenuItem = menu.findItem(R.id.menu_viewSettings);
        MenuItem favoriteMenuItem = menu.findItem(R.id.menu_viewFavorite);
        updateMenuTitle(viewSettingMenuItem);
        updateMenuTitle(favoriteMenuItem);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        String menuItemTitle = menuItem.getTitle().toString();
//        Log.e(TAG, "menuItemTitle is --> "+menuItemTitle);
        switch (menuItem.getItemId()) {
            case R.id.menu_viewSettings:
                if (Networks.isOnline()) {
                    if (menuItemTitle.equals(Constants.getMenu_ITEM_HIGHEST_RATED())) {
                        getTmbdHighestRatedMovies();
                        SharedPrefs.setSortOrder(Constants.getSortOrderHighestRated());
                    } else {
                        getTmbDPopularMovies();
                        SharedPrefs.setSortOrder(Constants.getSortOrderMostPopular());
                    }
                    updateMenuTitle(menuItem);
                    changeActivityTitle();
                } else {
                    Toast.makeText(this, R.string.txt_shld_online, Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menu_viewFavorite:
                SharedPrefs.setSortOrder(Constants.getSortOrderMyFavorites());
                updateMenuTitle(menuItem);
                changeActivityTitle();
                getFavoriteMoviesFromDataBase();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    // this method will update the title of the Menu (usually known as Setting menu) to
    // correspond to the related retrieved Movie list
    private void updateMenuTitle(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_viewSettings:
                if (!Constants.getTmdbApiKeyValue().isEmpty()) {
                    if (SharedPrefs.getSortOrder().equals(Constants.getSortOrderMostPopular())) {
                        menuItem.setTitle(Constants.getMenu_ITEM_HIGHEST_RATED());
                    } else {
                        menuItem.setTitle(Constants.getMenu_ITEM_MOST_POPULAR());
                    }
                } else {
                    menuItem.setTitle(R.string.txt_api_key_value_missing);
                }
                return;
            case R.id.menu_viewFavorite:
                menuItem.setTitle(Constants.getMenu_ITEM_FAVORITE_MOVIES());
                return;
            default:
                break;
        }
    }


    // ======== Menu ======== END ========

    // ======= ======= ======= ======= ======== ======= =======

    @Override
    public void moviesListParsed(List<MovieItem> movieItem) {
        Log.i(TAG, "moviesListParsed arrived & its size is :"+movieItem.size());
//        savedMovieItems.addAll(movieItem);
        adapter.updateList(movieItem);
    }

    @Override
    public void onMoviesListParsedError(String error) {
        Toast.makeText(this,R.string.txt_failed_to_get_movie_list, Toast.LENGTH_LONG).show();
    }

    // ======= ======= ======= ======= ======== ======= =======

    // ======= Get Movies FROM TMDB OR LOCAL DB ======= START ========
    private void getMovies() {
        if (SharedPrefs.getSortOrder().equals(Constants.getSortOrderMostPopular())) {
            getTmbDPopularMovies();
        } else if (SharedPrefs.getSortOrder().equals(Constants.getSortOrderHighestRated())){
            getTmbdHighestRatedMovies();
        }else{
            getFavoriteMoviesFromDataBase();
        }
    }

    // ======= Get Movies FROM TMDB OR LOCAL DB ======= END/FIN ========

    // ======= Get Popular Movies FROM TMDB ======= START ========
    private void getTmbDPopularMovies() {
        if (!Constants.getTmdbApiKeyValue().isEmpty()) {
            if (Networks.isOnline()) {
                URL getPopMovies = networks.buildUrl(Constants.getTmdbPopularQuery());
                new GetMoviesListAsync(this).execute(getPopMovies);
            } else {
                Toast.makeText(this, R.string.txt_shld_online, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.txt_api_key_value_missing, Toast.LENGTH_SHORT).show();
        }
    }

    // ======= Get Popular Movies FROM TMDB ======= END/FIN ========


    // ======= Get highest-rated Movies FROM TMDB ======= START ========
    public void getTmbdHighestRatedMovies() {
        if (!Constants.getTmdbApiKeyValue().isEmpty()) {
            if (Networks.isOnline()) {
                URL getPopMovies = networks.buildUrl(Constants.getTmdbTopRatedQuery());
                new GetMoviesListAsync(this).execute(getPopMovies);
            } else {
                Toast.makeText(this, R.string.txt_shld_online, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.txt_api_key_value_missing, Toast.LENGTH_SHORT).show();
        }
    }

    // ======= Get highest-rated Movies FROM TMDB ======= END/FIN ========

    // ======= Get Favorite Movies FROM Local DataBase ======= START ========

    // Room Database
    private void getFavoriteMoviesFromDataBase() {
        if (!Constants.getTmdbApiKeyValue().isEmpty()) {
            if (Networks.isOnline()) {
                setupMainViewModelForFavoriteMovies();
            } else {
                Toast.makeText(this, R.string.txt_shld_online, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.txt_api_key_value_missing, Toast.LENGTH_SHORT).show();
        }
    }

    // ======= Get Favorite Movies FROM Local DataBase ======= END/FIN ========

    // ======= Show Movie Details Activity ======= START ========

    private void showMovieDetails(MovieItem movie) {
//        Log.d(TAG, " movie: "+movie.toString());
        if (Networks.isOnline()) {
            Intent intent = new Intent(this, DetailedActivity.class);
            intent.putExtra(Constants.getMovieId(), movie.getId());
            startActivity(intent);
        }else{
            Toast.makeText(this,R.string.txt_conct_first,Toast.LENGTH_SHORT).show();
        }
    }

    // ======= Show Movie Details Activity ======= END/FIN ========

    private void setupMainViewModelForFavoriteMovies(){
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getAllFavoriteMovie().observe(this, new Observer<List<FavoriteMoviesEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteMoviesEntry> favoriteMoviesEntries) {
                Log.e(TAG, "Main Activity FavoriteMovies Set changed");
                Log.e(TAG, "Main Activity FavoriteMovies Set changed size = "+favoriteMoviesEntries.size());
                ArrayList<MovieItem> favoriteMovieItems = new ArrayList<>();
                for (int i = 0; i < favoriteMoviesEntries.size(); i++) {
                    MovieItem movieItem = new MovieItem();
                    movieItem.setId(favoriteMoviesEntries.get(i).getMovieID());
                    movieItem.setTitle(favoriteMoviesEntries.get(i).getMovieTitle());
                    movieItem.setPosterPath(favoriteMoviesEntries.get(i).getMoviePosterURL());
                    favoriteMovieItems.add(movieItem);
                }
                adapter.updateList(favoriteMovieItems);
            }
        });
    }
}

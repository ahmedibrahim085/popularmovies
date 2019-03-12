package com.ahmed.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.popularmovies.adapters.reviews.ReviewsAdapter;
import com.ahmed.popularmovies.adapters.trailers.TrailerAdapter;
import com.ahmed.popularmovies.database.FavoriteMoviesEntry;
import com.ahmed.popularmovies.models.MovieItem;
import com.ahmed.popularmovies.models.MovieReviewItem;
import com.ahmed.popularmovies.models.TrailerItem;
import com.ahmed.popularmovies.networkCalls.movieDetails.GetMovieDetailAsync;
import com.ahmed.popularmovies.networkCalls.movieDetails.GetMovieReviewsAsync;
import com.ahmed.popularmovies.networkCalls.movieDetails.GetMovieTrailersAsync;
import com.ahmed.popularmovies.networkCalls.movieDetails.OnMovieDetailsParsed;
import com.ahmed.popularmovies.networkCalls.movieDetails.OnMovieReviewsParsed;
import com.ahmed.popularmovies.networkCalls.movieDetails.OnMovieTrailersParsed;
import com.ahmed.popularmovies.utils.AppDatabase;
import com.ahmed.popularmovies.utils.AppExecutors;
import com.ahmed.popularmovies.utils.Constants;
import com.ahmed.popularmovies.utils.Networks;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity implements OnMovieDetailsParsed,
        OnMovieTrailersParsed , OnMovieReviewsParsed {

    private static String TAG = DetailedActivity.class.getSimpleName();
    private Networks networks = new Networks();
    private int movieItemID;
    MovieItem movieItemDetails;
    ArrayList<TrailerItem> trailers;
    ArrayList<MovieReviewItem> movieReviewItems;
    private String moviePosterURL = "";
    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieRunTime;
    private TextView movieVoteAverage;
    private TextView movieDescription;
    private TextView tv_trailer;
    private TextView tv_reviews;
    private Button btn_favorite;

    RecyclerView trailers_recyclerView;
    private TrailerAdapter trailers_adapter;

    RecyclerView reviews_recyclerView;
    private ReviewsAdapter reviews_adapter;

    // Member variable for the app database
    private AppDatabase appDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent intent = getIntent();
        movieItemID = intent.getIntExtra(Constants.getMovieId(),0);
        initDb();
        initUI();
        if (savedInstanceState != null) {
            //movieItemDetail
            movieItemDetails = (MovieItem) savedInstanceState.getSerializable("movieItemDetails");
            if (movieItemDetails == null) {
                getMovieDetails(movieItemID);
            }else{
                movieDetailsParsed(movieItemDetails);
            }
            //trailers
            trailers = (ArrayList<TrailerItem>) savedInstanceState.getSerializable("trailers");
            if (trailers == null) {
                getMovieTrailers(movieItemID);
            }else{
                movieTrailersParsed(trailers);
            }
            //movie Reviews
            movieReviewItems = (ArrayList<MovieReviewItem>) savedInstanceState.getSerializable("movieReviewItems");
            if (movieReviewItems == null) {
                getMovieReviews(movieItemID);
            }else{
                movieReviewsParsed(movieReviewItems);
            }
        } else {
            getMovieDetails(movieItemID);
            getMovieTrailers(movieItemID);
            getMovieReviews(movieItemID);
        }
    }

    // ======= ======= ======= ======= ======== ======= =======
    // ======= ======= ======= ======= ======== ======= =======
    // ======= ======= ======= User Interface ======== =======
    // ======= ======= ======= ======= ======== ======= =======
    // ======= ======= ======= ======= ======== ======= =======

    private void initUI(){
        changeActivityTitle();
        moviePoster = findViewById(R.id.iv_movie_details_poster);
        movieTitle = findViewById(R.id.tv_movie_title);
        movieReleaseDate = findViewById(R.id.tv_movie_release_date);
        movieRunTime = findViewById(R.id.tv_movie_runtime);
        movieVoteAverage = findViewById(R.id.tv_movie_vote_average);
        movieDescription = findViewById(R.id.tv_movie_description);
        tv_trailer = findViewById(R.id.tv_trailers);
        tv_reviews = findViewById(R.id.tv_review);
        btn_favorite = findViewById(R.id.btn_favorite);
        initiateTrailersRecyclerView();
        initiateReviewsRecyclerView();
        initFavoriteButton();
    }

    private void initiateReviewsRecyclerView() {
        reviews_recyclerView = findViewById(R.id.review_recycler_view);
        // Add layout manager to manage layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reviews_recyclerView.setLayoutManager(linearLayoutManager);
        // Add Divider & Separator
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(reviews_recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        reviews_recyclerView.addItemDecoration(dividerItemDecoration);
        // Add trailers_adapter to handle data from data source to view
        reviews_adapter = new ReviewsAdapter(new ArrayList<MovieReviewItem>());
        reviews_recyclerView.setAdapter(reviews_adapter);
    }

    // ======= Recycler VIEW ======= START ========
    private void initiateTrailersRecyclerView(){
        trailers_recyclerView = findViewById(R.id.trailer_recycle_view);
        // Add layout manager to manage layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        trailers_recyclerView.setLayoutManager(linearLayoutManager);
        // Add Divider & Separator
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(trailers_recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        trailers_recyclerView.addItemDecoration(dividerItemDecoration);
        // Add trailers_adapter to handle data from data source to view
        trailers_adapter = new TrailerAdapter(new ArrayList<TrailerItem>());
        trailers_recyclerView.setAdapter(trailers_adapter);
        trailers_adapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                TrailerItem trailerItem = trailers_adapter.getMovieItems(position);
                playTrailer(trailerItem);
            }
        });
    }

    private void initFavoriteButton(){
        isFavoriteMovie(movieItemID);
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMovieFavoriteState(movieItemID);
            }
        });
    }

    private void changeActivityTitle() {
        this.setTitle(Constants.getActivityMovieDetails());
    }

    // ======= ======= ======= ======= ======== ======= =======
    // ======= ======= ======= ======= ======== ======= =======
    // ======= ======= ======= Network ======== ======= =======
    // ======= ======= ======= ======= ======== ======= =======
    // ======= ======= ======= ======= ======== ======= =======


    // ======= ======= ======= ======= ======== ======= =======
    // ======= Get A Movie Details ======= START ========
    private void getMovieDetails(int movieDetail){
        URL getMovieDetails = networks.buildMovieDetailsUrl(movieDetail);
        new GetMovieDetailAsync(this).execute(getMovieDetails);
    }


    @Override
    public void movieDetailsParsed(MovieItem movieItem) {
        // Movie Title will be on the top of the Activity
        movieItemDetails = movieItem;
        if(movieItemDetails !=null) {
            movieTitle.setText(movieItemDetails.getTitle());
            // Release date to be manipulated to get only the year
            String releaseDate = movieItemDetails.getReleaseDate();
            releaseDate = releaseDate.substring(0, Math.min(releaseDate.length(), 4));
            movieReleaseDate.setText(releaseDate);
            // Movie Average vote
            String voteAverage = movieItemDetails.getVoteAverage() + "/10";
            movieVoteAverage.setText(voteAverage);
            // Movie Description
            movieDescription.setText(movieItemDetails.getOverview());
            // movieRunTime to be in Minuit
            String runTime = String.valueOf(movieItem.getRunTime()+ "min");
            movieRunTime.setText(runTime);
            // Load Movie Poster
            moviePosterURL = movieItemDetails.getPosterPath();
            Picasso.get()
                    .load(Networks.getsPosterPathBuilt(moviePosterURL))
                    .into(moviePoster);
        }else{
            Toast.makeText(App.getAppContext(),R.string.tst_err_movie_details,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMovieDetailsParsedError(String error) {
        Toast.makeText(this,"Failed To get Movie Details", Toast.LENGTH_LONG).show();
    }
    // ======= Get A Movie Details ======= END/FIN ========

    // ======= ======= ======= ======= ======== ======= =======

    // ======= Get Movie Trailers list ======= START ========

    private void getMovieTrailers(int movieId){
        URL movieTrailerURL = networks.getMovieVideosURL(movieId);
        new GetMovieTrailersAsync(this).execute(movieTrailerURL);
    }

    @Override
    public void movieTrailersParsed(List <TrailerItem> movieTrailers) {
//        Log.e(TAG, "Movie = Trailers = arrived & they are \n: "+movieTrailers.toString());
        if (movieTrailers !=null && !movieTrailers.isEmpty()){
            trailers = new ArrayList<>();
            trailers.addAll(movieTrailers);
            trailers_adapter.updateList(movieTrailers);
        }else {
            tv_trailer.setText(getResources().getString(R.string.tv_trailers_err));
        }
    }

    @Override
    public void onMovieTrailersParsedError(String error) {
        Toast.makeText(this,R.string.txt_error_getting_movie_trailer, Toast.LENGTH_LONG).show();
        tv_trailer.setText(getResources().getString(R.string.tv_get_trailers_err));
    }

    // ======= Get Movie Trailers list  ======= END/FIN ========

    // ======= ======= ======= ======= ======== ======= =======

    // ======= Get Movie Reviews list ======= START ========

    private void getMovieReviews(int movieId){
        URL movieReviewsURL = networks.getMovieReviewURL(movieId);
        new GetMovieReviewsAsync(this).execute(movieReviewsURL);
    }

    @Override
    public void movieReviewsParsed(List<MovieReviewItem> movieReviewItems) {
//        Log.e(TAG, "Movie = Reviews = arrived & they are \n: "+movieReviewItems.toString());
        if (!movieReviewItems.isEmpty()){
            reviews_adapter.updateList(movieReviewItems);
        }else {
            tv_reviews.setGravity(Gravity.CENTER);
            tv_reviews.setText(getResources().getString(R.string.tv_reviews_err));
        }
    }

    @Override
    public void onMovieReviewParsedError(String error) {
        Toast.makeText(this,R.string.txt_error_getting_movie_reviews, Toast.LENGTH_LONG).show();
    }

    // ======= Get Movie Reviews list  ======= END/FIN ========

    // ======= ======= ======= ======= ======== ======= =======

    // ======= ======= ======= ======= ======== ======= =======
    // ======= ======= Support Functions ======= =======
    // ======= ======= ======= ======= ======== ======= =======

    // ======= ======= Play Movie trailer in Youtube
    private void playTrailer(TrailerItem trailer){
        Log.d(TAG, " trailer: "+trailer.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.getYoutubeWatchBase
                ()+trailer.getKey()));
        Intent chooser = Intent.createChooser(intent , "Open With");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else {
            startActivity(chooser);
        }
    }

    // ======= ======= Add Remove a Movie To Favorites =======
   private void switchMovieFavoriteState(int movieId){
        String btnTxt = String.valueOf(btn_favorite.getText());
       String notFavorite = this.getResources().getString(R.string.bt_mark_as_not_favorite_movie);

        if(btnTxt.equals(notFavorite)){
            addMovieToFavoriteList(movieId , movieItemDetails.getTitle(),
                    movieItemDetails.getPosterPath());
        }else{
            removeMovieFromFavoriteList(movieId , movieItemDetails.getTitle(),
                    movieItemDetails.getPosterPath());
        }
    }

    private void updateFavoriteButton(boolean movieFavoriteState){
        if (movieFavoriteState) {
                btn_favorite.setText(R.string.bt_mark_as_favorite_movie);
        }else{
            btn_favorite.setText(R.string.bt_mark_as_not_favorite_movie);
        }
        if (btn_favorite.getVisibility()== View.INVISIBLE){
            btn_favorite.setVisibility(View.VISIBLE);
        }
    }

    // ======= ======= Database
    private void initDb(){
        appDatabase = AppDatabase.getsInstance(this.getApplicationContext());
    }

    private void addMovieToFavoriteList(int favMovieID, @NonNull String favMovieTitle, @NonNull
            String favMoviePosterURL){
        // old way
        // todo verify the corner case before supmission
        final FavoriteMoviesEntry favoriteMovie = new FavoriteMoviesEntry(favMovieID , favMovieTitle, favMoviePosterURL );
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.favoriteMoviesDao().addFavoriteMovie(favoriteMovie);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateFavoriteButton(true);
                    }
                });
            }
        });
    }

    private void removeMovieFromFavoriteList(int favMovieID, @NonNull String favMovieTitle, @NonNull
            String favMoviePosterURL){
        final FavoriteMoviesEntry favoriteMovie = new FavoriteMoviesEntry(favMovieID , favMovieTitle, favMoviePosterURL );
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.favoriteMoviesDao().removeFavoriteMovie(favoriteMovie);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateFavoriteButton(false);
                    }
                });
            }
        });
    }

   private void isFavoriteMovie(final int favMovieID) {
       final LiveData<List<FavoriteMoviesEntry>> favoriteMovie = appDatabase
               .favoriteMoviesDao().getFavoriteMovie(favMovieID);
       favoriteMovie.observe(this, new Observer<List<FavoriteMoviesEntry>>() {
           @Override
           public void onChanged(@Nullable List<FavoriteMoviesEntry> favoriteMoviesEntries) {
               if (favoriteMoviesEntries != null && !favoriteMoviesEntries.isEmpty()) {
                   Log.e(TAG, "Detailed Activity FavoriteMovies Set changed");
                   Log.e(TAG, "Detailed Activity FavoriteMovies Set changed size = " + favoriteMoviesEntries.size());
                   for (int i = 0; i < favoriteMoviesEntries.size(); i++) {
                       if(favoriteMoviesEntries.get(i).getMovieID() == favMovieID){
                           updateFavoriteButton(true);
                           return;
                       }
                   }
                   updateFavoriteButton(false);
               }else {
                   updateFavoriteButton(false);
               }
           }
       });
    }
}


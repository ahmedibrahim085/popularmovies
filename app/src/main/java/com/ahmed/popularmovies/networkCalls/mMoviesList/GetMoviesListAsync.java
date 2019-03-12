package com.ahmed.popularmovies.networkCalls.mMoviesList;

import android.os.AsyncTask;

import com.ahmed.popularmovies.models.MovieItem;
import com.ahmed.popularmovies.models.MoviesResponse;
import com.ahmed.popularmovies.utils.Networks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GetMoviesListAsync extends AsyncTask<URL, Void, String> {

    private static OnMoviesListParsed onMoviesListParsed;
    public GetMoviesListAsync(OnMoviesListParsed onMoviesListParsed) {
        GetMoviesListAsync.onMoviesListParsed = onMoviesListParsed;
    }

    public static OnMoviesListParsed getOnMoviesListParsed() {
        return onMoviesListParsed;
    }

    private static String TAG = GetMoviesListAsync.class.getSimpleName();

    private String moviePopularResults;

    private static final String RESULT_PAGE = "page";
    private static final String RESULT_TOTAL = "total_results";
    private static final String RESULT_TOTAL_PAGES = "total_pages";
    private static final String RESULT__MOVIE_RESULTS = "results";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected String doInBackground(URL... params) {
        URL movieListURL = params[0];

        try {
            moviePopularResults = Networks.getResponseFromHttpUrl(movieListURL);
//            Log.d(TAG, "Call Response is ->>>> "+moviePopularResults+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviePopularResults;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s!=null) {
//        Log.d(TAG, " ==================== onPostExecute ============================= " + "\n");
            JSONObject jsonObject = new JSONObject();

            if (s != null && !s.isEmpty()) {
                try {
                    jsonObject = new JSONObject(s);
//                Log.d(TAG, " ==================== onPostExecute jsonObject " + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MoviesResponse moviesResponse = new MoviesResponse();

                moviesResponse.setPage(jsonObject.optInt(RESULT_PAGE));
                moviesResponse.setTotalPages(jsonObject.optInt(RESULT_TOTAL_PAGES));
                moviesResponse.setTotalResults(jsonObject.optInt(RESULT_TOTAL));
                JSONArray moviesArrayResults = jsonObject.optJSONArray(RESULT__MOVIE_RESULTS);


                if (moviesArrayResults != null) {
                    moviesResponse.setResults(new ArrayList<MovieItem>());
                    for (int i = 0; i < moviesArrayResults.length(); i++) {
                        try {
                            JSONObject object = new JSONObject(moviesArrayResults.get(i).toString());

                            moviesResponse.getResults().add(MovieItem.getMovieItemFromJson(object));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
//                Log.d(TAG, "onPostExecute -: resultsItem ->" + moviesResponse.getTrailerItemList());
                    onMoviesListParsed.moviesListParsed(moviesResponse.getResults());
                }
            }
        }else{
            onMoviesListParsed.onMoviesListParsedError("Parsing Error");
        }
    }

}

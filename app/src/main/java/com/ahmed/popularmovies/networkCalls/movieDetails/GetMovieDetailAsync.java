package com.ahmed.popularmovies.networkCalls.movieDetails;

import android.os.AsyncTask;

import com.ahmed.popularmovies.models.MovieItem;
import com.ahmed.popularmovies.utils.Networks;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class GetMovieDetailAsync extends AsyncTask<URL, Void, MovieItem> {

    private static String TAG = GetMovieDetailAsync.class.getSimpleName();
    private OnMovieDetailsParsed onMovieDetailsParsed;
    private String movieDetailsResults;

    public GetMovieDetailAsync(OnMovieDetailsParsed OnMovieDetailsParsed) {
        this.onMovieDetailsParsed = OnMovieDetailsParsed;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MovieItem doInBackground(URL... urls) {
        URL movieDetailsUrl = urls[0];
        MovieItem movieItem = new MovieItem();
        try {
            movieDetailsResults = Networks.getResponseFromHttpUrl(movieDetailsUrl);
//            Log.d(TAG, "movieDetailsResults Response is ->>>> "+movieDetailsResults+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (movieDetailsResults !=null) {
            try {
                JSONObject jsonObject = new JSONObject(movieDetailsResults);
                if (jsonObject != null) {
                    movieItem = MovieItem.getMovieItemFromJson(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movieItem ;
    }

    @Override
    protected void onPostExecute(MovieItem movieDetails) {
        super.onPostExecute(movieDetails);
/*        Log.d(TAG, " ==================== onPostExecute ============================= " + "\n");*/
//        Log.d(TAG, " === movieDetailsParsed === onPostExecute String ===" + movieDetails);
        if (movieDetails !=null) {
            onMovieDetailsParsed.movieDetailsParsed(movieDetails);
        }else{
            onMovieDetailsParsed.onMovieDetailsParsedError("Movie Details Parsing Error");
        }

    }

}

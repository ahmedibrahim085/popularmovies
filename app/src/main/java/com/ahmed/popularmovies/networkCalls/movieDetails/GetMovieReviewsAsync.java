package com.ahmed.popularmovies.networkCalls.movieDetails;

import android.os.AsyncTask;
import android.util.Log;

import com.ahmed.popularmovies.R;
import com.ahmed.popularmovies.models.MovieReviewItem;
import com.ahmed.popularmovies.models.MovieReviewResponse;
import com.ahmed.popularmovies.utils.Networks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetMovieReviewsAsync extends AsyncTask<URL, Void, List<MovieReviewItem>> {

    private static String TAG = GetMovieReviewsAsync.class.getSimpleName();
    private OnMovieReviewsParsed onMovieReviewsParsed;
    private String movieReviewsResults;

    public GetMovieReviewsAsync(OnMovieReviewsParsed onMovieReviewsParsed) {
        this.onMovieReviewsParsed = onMovieReviewsParsed;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<MovieReviewItem> doInBackground(URL... urls) {
        URL movieReviewsUrl = urls[0];
        MovieReviewResponse movieReviewResponse = new MovieReviewResponse();
        List<MovieReviewItem> movieReviewItemList = null;
        try {
            movieReviewsResults = Networks.getResponseFromHttpUrl(movieReviewsUrl);
//            Log.e(TAG, " movie Reviews Results is : -> "+movieReviewsResults.toString());
            JSONObject jsonObject = new JSONObject(String.valueOf(movieReviewsResults));
            // --- Review ID ---
            movieReviewResponse.setId(Integer.valueOf(jsonObject.getString("id")));
            // --- Review is paged in page number x ---
            movieReviewResponse.setPage(jsonObject.getInt("page"));
            // --- Review total number of pages ---
            movieReviewResponse.setTotal_pages(jsonObject.getInt("total_pages"));
            // --- Review total number of Reviews ---
            movieReviewResponse.setTotal_results(jsonObject.getInt("total_results"));
            // --- Review total number of Reviews in Full/details ---
            movieReviewItemList = getMovieReviewsList(jsonObject);
            movieReviewResponse.setResults(movieReviewItemList);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return movieReviewItemList;
    }

    private List<MovieReviewItem> getMovieReviewsList(JSONObject jsonObject) {
        MovieReviewResponse movieReviewResponse = new MovieReviewResponse();

        JSONArray jsonArray = jsonObject.optJSONArray("results");
        movieReviewResponse.setResults(new ArrayList<MovieReviewItem>());
        if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(jsonArray.get(i).toString());

                        movieReviewResponse.getResults().add(
                                MovieReviewItem.getMovieReviewItemFromJson((object)));
                        Log.d(TAG, "Review Response filled now by  :" + object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Final movieReviewResponse :" + movieReviewResponse.getResults()
                        .toString());
            }
        return movieReviewResponse.getResults();
    }


    @Override
    protected void onPostExecute(List<MovieReviewItem> movieReviewResponse) {
        super.onPostExecute(movieReviewResponse);
        if (movieReviewResponse !=null) {
            onMovieReviewsParsed.movieReviewsParsed(movieReviewResponse);
        }else{
            onMovieReviewsParsed.onMovieReviewParsedError(String.valueOf(R.string
                    .txt_error_parsing_movie_reviews_list));
        }

    }

}

package com.ahmed.popularmovies.networkCalls.movieDetails;

import android.os.AsyncTask;

import com.ahmed.popularmovies.models.TrailerItem;
import com.ahmed.popularmovies.models.TrailerResponse;
import com.ahmed.popularmovies.utils.Networks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetMovieTrailersAsync extends AsyncTask<URL, Void, List<TrailerItem> > {

    private static String TAG = GetMovieTrailersAsync.class.getSimpleName();
    private OnMovieTrailersParsed onMovieTrailersParsed;
    private String movieTrailers;

    public GetMovieTrailersAsync(OnMovieTrailersParsed onMovieTrailersParsed) {
        this.onMovieTrailersParsed = onMovieTrailersParsed;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<TrailerItem> doInBackground(URL... urls) {
        URL movieDetailsUrl = urls[0];
       List<TrailerItem> trailerItemList = null;

        try {
            movieTrailers = Networks.getResponseFromHttpUrl(movieDetailsUrl);
            trailerItemList = getTrailerList(movieTrailers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trailerItemList;
    }

    @Override
    protected void onPostExecute(List<TrailerItem> trailersList) {
        super.onPostExecute(trailersList);
        if (trailersList !=null) {
            onMovieTrailersParsed.movieTrailersParsed(trailersList);
        }else{
            onMovieTrailersParsed.onMovieTrailersParsedError("Failed to Parse Trailer List");
        }

    }

    private List<TrailerItem> getTrailerList(String movieTrailers){
        TrailerResponse trailerResponse = new TrailerResponse();
        try {
            JSONObject jsonObject = new JSONObject(String.valueOf(movieTrailers));
            trailerResponse.setId(jsonObject.getInt("id"));
//            Log.e(TAG, " Movie trailers List ID is : " + trailerResponse.getId());

            JSONArray jsonArray = jsonObject.optJSONArray("results");
            if (jsonArray != null) {
                trailerResponse.setTrailerItemList(new ArrayList<TrailerItem>());
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(jsonArray.get(i).toString());

                        trailerResponse.getTrailerItemList().add(
                                TrailerItem.getTrailerItemFromJson((object)));
//                        Log.d(TAG, "trailerResponse filled now by  :" + object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                Log.d(TAG, "Final trailerResponse :" + trailerResponse.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailerResponse.getTrailerItemList();
    }
}

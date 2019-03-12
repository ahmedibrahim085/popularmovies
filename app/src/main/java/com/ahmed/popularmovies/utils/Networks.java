package com.ahmed.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;

import com.ahmed.popularmovies.App;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * These utility class will be used to communicate with the network.
 */
public class Networks {

    private String TAG = Networks.class.getSimpleName();


    /**
     * Builds the URL used to query the TMDB List.
     *
     * @return The URL to use to query the TMDB List.
     */
    public URL buildUrl(String path) {

        Uri builtUri = Uri.parse(Constants.getTmdbBaseUrlv3()).buildUpon()
                .appendPath(path)
                .appendQueryParameter(Constants.getTmdbApiKey(),Constants.getTmdbApiKeyValue())
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(TAG, "The build URL is - >> : "+url);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.toString());

        }
        return url;
    }

    /**
     * Builds the URL used to query the TMDB Movie & Movie details.
     *
     * @return The URL to use to query the TMDB Movie & Movie Details.
     */
    public URL buildMovieDetailsUrl(int movieId) {
        Uri builtUri;
            builtUri = Uri.parse(Constants.getTmdbBaseUrlv3()).buildUpon()
                    .appendPath(String.valueOf(movieId))
                    .appendQueryParameter(Constants.getTmdbApiKey(),Constants.getTmdbApiKeyValue())
                    .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(TAG, "The build Movie Detail URL is - >> : "+url);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.toString());
        }
        return url;
    }

    public URL getMovieVideosURL(int movieId) {
        Uri builtUri = Uri.parse(Constants.getTmdbBaseUrlv3()).buildUpon()
                    .appendPath(String.valueOf(movieId))
                    .appendPath(Constants.getTmdbMovieVideosQuery())
                    .appendQueryParameter(Constants.getTmdbApiKey(),Constants.getTmdbApiKeyValue())
                    .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(TAG, "The get Movie Videos URL is - >> : "+url);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.toString());
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getsPosterPathBuilt(String posterPath){
        return Constants.getTmbdImagesBaseUrl()+posterPath;
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) App.getAppContext().getSystemService(Context
                        .CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public URL getMovieReviewURL(int movieId) {
        Uri builtUri = Uri.parse(Constants.getTmdbBaseUrlv3()).buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath(Constants.getTmdbMovieReviewsQuery())
                .appendQueryParameter(Constants.getTmdbApiKey(),Constants.getTmdbApiKeyValue())
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(TAG, "The get Movie Reviews URL is - >> : "+url);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.toString());
        }
        return url;
    }
}
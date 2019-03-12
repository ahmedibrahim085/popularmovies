package com.ahmed.popularmovies.utils;

public class Constants {

    private Constants() {

    }
    // ======= URL needed for internet ======= START =======
    final static private String TMDB_BASE_URL_v3 = "https://api.themoviedb.org/3/movie/";

    final static private String TMBD_IMAGES_BASE_URL = "https://image.tmdb.org/t/p/w185";

    final static private String TMDB_API_KEY = "api_key";
    final static private String TMDB_API_KEY_VALUE = "";

    final static private String TMDB_POPULAR_QUERY = "popular";

    final static private String TMDB_TOP_RATED_QUERY = "top_rated";

    final static private String TMDB_MOVIE_VIDEOS_QUERY = "videos";

    final static private String TMDB_MOVIE_REVIEWS_QUERY = "reviews";


    final static private String YOUTUBE_WATCH_BASE = "https://www.youtube.com/watch?v=";



    // ======= URL needed for internet ======= END / FIN =======

    // ======= SharedPreferences Constants ======= START =======

    final private static String PREF_SORT_ORDER_KEY = "SORT_ORDER";
    final private static String SORT_ORDER_MOST_POPULAR = "MOST_POPULAR";
    final static private String SORT_ORDER_HIGHEST_RATED = "HIGHEST_RATED";
    final private static String SORT_ORDER_MY_FAVORITES = "MY_FAVORITES";

    // ======= SharedPreferences Constants ======= END / FIN =======

    // ======= Menu ITEM ======= START =======
    final private static String Menu_ITEM_MOST_POPULAR = "MOST POPULAR";
    final private static String Menu_ITEM_HIGHEST_RATED = "HIGHEST RATED";
    final private static String Menu_ITEM_FAVORITE_MOVIES = "FAVORITE MOVIES";
    // ======= Menu ITEM ======= END / FIN =======

    // ======= Movie Details ======= START =======
    final private static String MOVIE_DETAILS = "MOVIE_DETAILS";
    final private static String MOVIE_ID = "MOVIE_ID";
    final private static String ACTIVITY_MOVIE_DETAILS = "MOVIE DETAILS";
    // ======= Movie Details ======= END / FIN =======


    public static String getTmdbBaseUrlv3() {
        return TMDB_BASE_URL_v3;
    }


    public static String getTmbdImagesBaseUrl() {
        return TMBD_IMAGES_BASE_URL;
    }

    public static String getTmdbApiKey() {
        return TMDB_API_KEY;
    }

    public static String getTmdbApiKeyValue() {
        return TMDB_API_KEY_VALUE;
    }

    public static String getTmdbPopularQuery() {
        return TMDB_POPULAR_QUERY;
    }

    public static String getTmdbTopRatedQuery() {
        return TMDB_TOP_RATED_QUERY;
    }

    public static String getPrefSortOrderKey() {
        return PREF_SORT_ORDER_KEY;
    }

    public static String getSortOrderMostPopular() {
        return SORT_ORDER_MOST_POPULAR;
    }

    public static String getSortOrderHighestRated() {
        return SORT_ORDER_HIGHEST_RATED;
    }

    public static String getSortOrderMyFavorites() {
        return SORT_ORDER_MY_FAVORITES;
    }

    public static String getMenu_ITEM_MOST_POPULAR() {
        return Menu_ITEM_MOST_POPULAR;
    }

    public static String getMenu_ITEM_HIGHEST_RATED() {
        return Menu_ITEM_HIGHEST_RATED;
    }

    public static String getMenu_ITEM_FAVORITE_MOVIES() {
        return Menu_ITEM_FAVORITE_MOVIES;
    }

    public static String getMovieDetails() {
        return MOVIE_DETAILS;
    }

    public static String getActivityMovieDetails() {
        return ACTIVITY_MOVIE_DETAILS;
    }

    public static String getTmdbMovieVideosQuery() {
        return TMDB_MOVIE_VIDEOS_QUERY;
    }

    public static String getYoutubeWatchBase() {
        return YOUTUBE_WATCH_BASE;
    }
    public static String getMovieId() {
        return MOVIE_ID;
    }

    public static String getTmdbMovieReviewsQuery() {
        return TMDB_MOVIE_REVIEWS_QUERY;
    }
}

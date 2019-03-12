package com.ahmed.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ahmed.popularmovies.App;

// In this class I am trying to have a unified access across the app for the shared preference to
// avoid having multiple instances from it.
public class SharedPrefs {

    private static SharedPreferences mSharedPreferences;

    private static String SORT_ORDER = Constants.getSortOrderMostPopular();


    public static void InitiateAppSharedPrefs(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(context.getPackageName() + "_PREFS",
                    Context.MODE_PRIVATE);
        }
    }


    public static synchronized SharedPreferences getInstance() {
        if (getsSharedPreferences() == null) {
            setsSharedPreferences(
                    App.getAppContext().getSharedPreferences(App.getUniqueApplicationPrefsId(),
                            Context.MODE_PRIVATE));
        }
        return getsSharedPreferences();
    }

    private static SharedPreferences getsSharedPreferences() {
        return mSharedPreferences;
    }

    private static void setsSharedPreferences(SharedPreferences mSharedPreferences) {
        SharedPrefs.mSharedPreferences = mSharedPreferences;
    }

    public static String getSortOrder() {
        return getsSharedPreferences().getString(Constants.getPrefSortOrderKey(), SORT_ORDER);
    }

    public static void setSortOrder(String sortOrder) {
        getsSharedPreferences().edit().putString(Constants.getPrefSortOrderKey(), sortOrder).apply();
    }

}

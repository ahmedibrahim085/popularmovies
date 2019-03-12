package com.ahmed.popularmovies;

import android.app.Application;
import android.content.Context;

import com.ahmed.popularmovies.utils.AppDatabase;
import com.ahmed.popularmovies.utils.SharedPrefs;

public  class App extends Application {

    private static Context appContext;
    private static String UNIQUE_APPLICATION_PREFS_ID;
    private static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        setAppContext(this.getApplicationContext());
        setAppDatabase(AppDatabase.getsInstance(getAppContext()));
        setUniqueApplicationPrefsId(getAppContext().getPackageName()+"PREFS");
        initiateAppSharedPrefs(getAppContext());

    }

    private void initiateAppSharedPrefs(Context context){
        SharedPrefs.InitiateAppSharedPrefs(context);
        SharedPrefs.getInstance();
    }

    public static Context getAppContext() {
        return appContext;
    }

    private void setAppContext(Context appContext) {
        App.appContext = appContext;
    }

    public static String getUniqueApplicationPrefsId() {
        return UNIQUE_APPLICATION_PREFS_ID;
    }

    private static void setUniqueApplicationPrefsId(String uniqueApplicationPrefsId) {
        UNIQUE_APPLICATION_PREFS_ID = uniqueApplicationPrefsId;
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static void setAppDatabase(AppDatabase appDatabase) {
        App.appDatabase = appDatabase;
    }
}

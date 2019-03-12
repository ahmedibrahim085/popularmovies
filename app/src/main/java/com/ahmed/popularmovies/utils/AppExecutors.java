package com.ahmed.popularmovies.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static AppExecutors ourInstance;
    private static final Object LOCK = new Object();
    private final Executor diskIO;

    private AppExecutors(Executor disk_IO) {
        this.diskIO = disk_IO;
    }
    public static AppExecutors getInstance() {
        if (ourInstance == null){
            synchronized (LOCK){
                ourInstance = new AppExecutors(
                        Executors.newSingleThreadExecutor());
            }
        }
        return ourInstance;
    }

    public Executor getDiskIO(){
        return diskIO;
    }
}

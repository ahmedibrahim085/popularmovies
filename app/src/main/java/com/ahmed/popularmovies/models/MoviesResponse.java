package com.ahmed.popularmovies.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class MoviesResponse implements Serializable {

    private static String TAG = MoviesResponse.class.getSimpleName();

    private int page;
    private int totalPages;
    private List<MovieItem> results;
    private int totalResults;

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setResults(List<MovieItem> results) {
        this.results = results;
    }

    public List<MovieItem> getResults() {
        return results;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "{" +
                        "\"page = \"" + ":" + "\"" + page + "\"" +
                        ",\"totalPages = \"" + ":" + "\"" + totalPages + "\"" +
                        ",\"results = \"" + ":" + "\"" + results + "\"" +
                        ",\"total_results = \"" + ":" + "\"" + totalResults + ":" + "\""
                        + "}";
    }
}
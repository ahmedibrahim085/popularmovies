package com.ahmed.popularmovies.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class MovieReviewResponse implements Serializable {

    private int id;
    private List<MovieReviewItem> results;
    private int page;
    private int total_pages;
    private int total_results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieReviewItem> getResults() {
        return results;
    }

    public void setResults(List<MovieReviewItem> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieReviewResponse{" +
                "id=" + id +
                ", results=" + results +
                ", page=" + page +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}

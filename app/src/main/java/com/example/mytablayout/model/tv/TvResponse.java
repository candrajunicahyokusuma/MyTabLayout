package com.example.mytablayout.model.tv;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private ArrayList<TvItem> results;

    @SerializedName("total_results")
    private int totalResults;

    @Override
    public String toString() {
        return "TvResponse{" +
                "page=" + page +
                ", totalPages=" + totalPages +
                ", results=" + results +
                ", totalResults=" + totalResults +
                '}';
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<TvItem> getResults() {
        return results;
    }

    public void setResults(ArrayList<TvItem> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}

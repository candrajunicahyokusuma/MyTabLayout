package com.example.mytablayout.service;

import com.example.mytablayout.model.tv.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvApi {
    @GET("3/discover/tv?api_key=844f72718949941870e712bfc60a0549")
    Call<TvResponse> getTvs(@Query("language") String language);

    @GET("3/search/tv?api_key=844f72718949941870e712bfc60a0549")
    Call<TvResponse> getTVSearch(@Query("language") String language, @Query("query") String query);
}

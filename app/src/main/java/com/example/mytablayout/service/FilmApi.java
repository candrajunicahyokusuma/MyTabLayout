package com.example.mytablayout.service;

import com.example.mytablayout.model.movie.FilmResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmApi {
    @GET("3/discover/movie?api_key=844f72718949941870e712bfc60a0549")
    Call<FilmResponse> getFilms(@Query("language") String language);

    @GET("3/search/movie?api_key=844f72718949941870e712bfc60a0549")
    Call<FilmResponse> getFilmsSearch(@Query("language") String language, @Query("query") String query);

    @GET("3/discover/movie?api_key=844f72718949941870e712bfc60a0549")
    Call<FilmResponse> getFilmsRelease(@Query("primary_release_date.gte") String primary_release_date_gte, @Query("primary_release_date.lte") String primary_release_date_lte);

}

package com.example.mytablayout.service;

import com.example.mytablayout.service.FilmApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieService {
    Retrofit retrofit;
    public FilmApi getFilmApi(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FilmApi.class);
    }




}

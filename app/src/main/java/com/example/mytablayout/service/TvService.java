package com.example.mytablayout.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvService {
    Retrofit retrofit;
    public TvApi getTvApi(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TvApi.class);
    }
}

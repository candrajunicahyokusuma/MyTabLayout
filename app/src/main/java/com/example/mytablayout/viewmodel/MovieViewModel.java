package com.example.mytablayout.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.mytablayout.model.movie.FilmResponse;
import com.example.mytablayout.model.movie.ResultsItem;
import com.example.mytablayout.service.MovieService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private MovieService movieService;
    String language;
    private MutableLiveData<ArrayList<ResultsItem>> listFilm = new MutableLiveData<>();

    public LiveData<ArrayList<ResultsItem>> getFilm(){
        return listFilm;
    }

    public void loadMovie(String language) {
        this.language = language;
        if (this.movieService == null){
            movieService = new MovieService();
        }
        movieService.getFilmApi().getFilms(language).enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                if (response.isSuccessful()){
                    listFilm.setValue(response.body().getResults());
                }



            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {

            }
        });
    }
}

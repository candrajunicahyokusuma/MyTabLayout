package com.example.mytablayout.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.mytablayout.model.tv.TvItem;
import com.example.mytablayout.model.tv.TvResponse;
import com.example.mytablayout.service.TvService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvViewModel extends ViewModel {
    private TvService tvService;
    private String language;
    private MutableLiveData<ArrayList<TvItem>> listTv= new MutableLiveData<>();

    public MutableLiveData<ArrayList<TvItem>> getListTv() {
        return listTv;
    }
    public void loadTv(String language){
        if (tvService == null){
            tvService = new TvService();
        }
        tvService.getTvApi().getTvs(language).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()){
                    listTv.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
        });
    }
}

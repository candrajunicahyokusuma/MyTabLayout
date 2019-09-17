package com.example.mytablayout.fragment;


import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mytablayout.R;
import com.example.mytablayout.adapter.CardViewFilmAdapter;
import com.example.mytablayout.model.movie.ResultsItem;
import com.example.mytablayout.viewmodel.MovieViewModel;

import java.util.ArrayList;

;

public class MovieFragment extends Fragment {
    ProgressDialog progressDialog;
    private RecyclerView rvFilms;
    private CardViewFilmAdapter cardViewFilmAdapter;
    private Observer<ArrayList<ResultsItem>> getFilms = new Observer<ArrayList<ResultsItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ResultsItem> resultsItems) {
            if (resultsItems != null) {
                cardViewFilmAdapter.setData(resultsItems, getContext());
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardViewFilmAdapter = new CardViewFilmAdapter();
        cardViewFilmAdapter.notifyDataSetChanged();
        rvFilms = view.findViewById(R.id.rv_film);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);
        model.loadMovie(getResources().getString(R.string.language));
        model.getFilm().observe(this, getFilms);

        rvFilms.setLayoutManager(layoutManager);


        rvFilms.setAdapter(cardViewFilmAdapter);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);

    }
}
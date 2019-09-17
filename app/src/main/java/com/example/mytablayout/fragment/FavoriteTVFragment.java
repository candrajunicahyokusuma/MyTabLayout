package com.example.mytablayout.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mytablayout.R;
import com.example.mytablayout.adapter.FavoriteTvAdapter;
import com.example.mytablayout.database.AplicationDatabase;
import com.example.mytablayout.database.MovieDB;

import java.util.ArrayList;


public class FavoriteTVFragment extends Fragment {

    private RecyclerView rvFilms;

    private ArrayList<MovieDB> listfilm = new ArrayList<>();
    private AplicationDatabase aplicationDatabase;


    public FavoriteTVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FavoriteTvAdapter favoriteTvAdapter = new FavoriteTvAdapter();
        rvFilms = view.findViewById(R.id.rv_favorite_tv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        if (this.aplicationDatabase == null) {
            aplicationDatabase = AplicationDatabase.initDatabase(getContext());


        }
        rvFilms.setLayoutManager(layoutManager);
        listfilm.addAll(aplicationDatabase.movieDAO().getByCategory("tv"));
        favoriteTvAdapter.setData(listfilm, getContext());
        rvFilms.setAdapter(favoriteTvAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false);
    }


}

package com.example.mytablayout.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mytablayout.R;
import com.example.mytablayout.adapter.CardViewFilmAdapter;
import com.example.mytablayout.model.movie.ResultsItem;
import com.example.mytablayout.viewmodel.SearchMovieViewModel;

import java.util.ArrayList;

public class SearchMovieActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String FINAL_KEY = "search";
    RecyclerView rvSearch;
    String query = "";
    String searchString = "";
    SearchView searchView;
    CardViewFilmAdapter cardViewFilmAdapter;
    SearchMovieViewModel searchMovieViewModel;
    private Observer<ArrayList<ResultsItem>> getMovie = new Observer<ArrayList<ResultsItem>>() {


        @Override
        public void onChanged(@Nullable ArrayList<ResultsItem> resultsItems) {
            if (resultsItems != null) {
                cardViewFilmAdapter.setData(resultsItems, getApplicationContext());

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (savedInstanceState != null) {
            searchString = savedInstanceState.getString(FINAL_KEY);
        }
        rvSearch = findViewById(R.id.search_rv);
        rvSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        searchString = searchView.getQuery().toString();
        outState.putString(FINAL_KEY, searchString);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_item);
        searchView = (SearchView) searchItem.getActionView();
        if (searchString != null && !searchString.isEmpty()) {
            searchItem.expandActionView();
            searchView.setQuery(searchString, true);
            searchView.clearFocus();
            onSearch(searchString);
        } else {
            searchView.setOnQueryTextListener(this);
            searchView.setQueryHint("Masukan kata pencarian");
        }
        return super.onCreateOptionsMenu(menu);

    }

    public void onSearch(String query) {
        cardViewFilmAdapter = new CardViewFilmAdapter();
        cardViewFilmAdapter.notifyDataSetChanged();
        searchMovieViewModel = ViewModelProviders.of(this).get(SearchMovieViewModel.class);
        searchMovieViewModel.loadMovie(getResources().getString(R.string.language), query);
        searchMovieViewModel.getFilm().observe(this, getMovie);
        rvSearch.setAdapter(cardViewFilmAdapter);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            this.query = query;
            Log.d("SeachMovieActivity", query);
            onSearch(query);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean onQueryTextChange(String query) {
        try {
            this.query = query;
            Log.d("SeachMovieActivity", query);
            onSearch(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}

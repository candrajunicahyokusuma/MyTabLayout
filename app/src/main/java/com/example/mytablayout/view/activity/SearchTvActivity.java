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
import com.example.mytablayout.adapter.CardViewTvAdapter;
import com.example.mytablayout.model.tv.TvItem;
import com.example.mytablayout.viewmodel.SearchTvViewModel;

import java.util.ArrayList;

public class SearchTvActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView rvSearch ;
    String searchString="";
    SearchView searchView;
    CardViewTvAdapter cardViewTvAdapter;
    SearchTvViewModel searchTvViewModel;
    private static final String FINAL_KEY ="search";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(savedInstanceState !=null){
            searchString = savedInstanceState.getString(FINAL_KEY);
        }
        rvSearch = findViewById(R.id.search_rv_tv);
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
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search_item);
        searchView = (SearchView) searchItem.getActionView();
        if (searchString!=null && !searchString.isEmpty()){
            searchItem.expandActionView();
            searchView.setQuery(searchString, true);
            searchView.clearFocus();
            onSearch(searchString);
        }
        else {
            searchView.setOnQueryTextListener(this);
            searchView.setQueryHint("Masukan kata pencarian");
        }
        return super.onCreateOptionsMenu(menu);

    }

    public void onSearch(String query){
        cardViewTvAdapter = new CardViewTvAdapter();
        cardViewTvAdapter.notifyDataSetChanged();
        searchTvViewModel = ViewModelProviders.of(this).get(SearchTvViewModel.class);
        searchTvViewModel.loadTv(getResources().getString(R.string.language), query);
        searchTvViewModel.getListTv().observe(this, getTv);
        rvSearch.setAdapter(cardViewTvAdapter);

    }

    private Observer<ArrayList<TvItem>> getTv = new Observer<ArrayList<TvItem>>() {


        @Override
        public void onChanged(@Nullable ArrayList<TvItem> tvItems) {
            if (tvItems !=null){
                cardViewTvAdapter.setData(tvItems, getApplicationContext());

            }
        }


    };

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            Log.d("SeachTVActivity", query);
            onSearch(query);
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    @Override
    public boolean onQueryTextChange(String query) {
        try {
            Log.d("SeachTvActivity", query);
            onSearch(query);
            return true;
        }catch (Exception e){
            return  false;
        }
    }
}

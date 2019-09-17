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

import com.example.mytablayout.R;
import com.example.mytablayout.adapter.CardViewTvAdapter;
import com.example.mytablayout.model.tv.TvItem;
import com.example.mytablayout.viewmodel.TvViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    CardViewTvAdapter cardViewTvAdapter;
    ProgressDialog progressDialog;
    private Observer<ArrayList<TvItem>> getListTvs = new Observer<ArrayList<TvItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvItem> tvItems) {
            if (tvItems != null) {
                cardViewTvAdapter.setData(tvItems, getContext());
                progressDialog.dismiss();
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvTv = (RecyclerView) view.findViewById(R.id.rv_tv);
        rvTv.setHasFixedSize(true);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.
                VERTICAL);
        rvTv.setLayoutManager(layoutManager);
        cardViewTvAdapter = new CardViewTvAdapter();
        cardViewTvAdapter.notifyDataSetChanged();
        TvViewModel tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.loadTv(getResources().getString(R.string.language));
        tvViewModel.getListTv().observe(this, getListTvs);

        rvTv.setAdapter(cardViewTvAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }


}

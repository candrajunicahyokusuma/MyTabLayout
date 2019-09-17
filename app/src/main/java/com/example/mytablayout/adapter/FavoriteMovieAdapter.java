package com.example.mytablayout.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mytablayout.R;
import com.example.mytablayout.database.MovieDB;
import com.example.mytablayout.model.movie.ResultsItem;
import com.example.mytablayout.view.activity.DetailActivity;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {
    private ArrayList<MovieDB> listFilm = new ArrayList<>();
    private Context context;

    public void setData(ArrayList<MovieDB> list, Context context) {
        this.context = context;
        listFilm.clear();
        listFilm.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_film, viewGroup, false);
        return new FavoriteMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.ViewHolder viewHolder, int i) {
        Glide.with(viewHolder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w154" + listFilm.get(i).getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(viewHolder.imgPhoto);
        viewHolder.tvJudul.setText(listFilm.get(i).getTitle());

        viewHolder.tvRelease.setText(listFilm.get(i).getReleaseDate());
        final ResultsItem resultsItem = new ResultsItem();
        resultsItem.setId(listFilm.get(i).getId());
        resultsItem.setTitle(listFilm.get(i).getTitle());
        resultsItem.setReleaseDate(listFilm.get(i).getReleaseDate());
        resultsItem.setPosterPath(listFilm.get(i).getPosterPath());
        resultsItem.setOverview(listFilm.get(i).getOverview());
        viewHolder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(context, DetailActivity.class);
                moveIntent.putExtra(DetailActivity.EXTRA_FILM, resultsItem);
                context.startActivity(moveIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFilm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvJudul, tvRelease;
        CardView cvMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvJudul = itemView.findViewById(R.id.tv_item_name);
            tvRelease = itemView.findViewById(R.id.tv_item_status);
            cvMain = itemView.findViewById(R.id.card_view);
        }
    }
}

package com.example.mytablayout.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.example.mytablayout.model.tv.TvItem;
import com.example.mytablayout.view.activity.DetailTvActivity;

import java.util.ArrayList;

public class CardViewTvAdapter extends RecyclerView.Adapter<CardViewTvAdapter.CardViewViewHolder> {
    private ArrayList<TvItem> listTv = new ArrayList<TvItem>();
    private Context context;

    public CardViewTvAdapter() {

    }

    public void setData(ArrayList<TvItem> list, Context context) {
        this.context = context;
        this.listTv.clear();
        this.listTv.addAll(list);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewTvAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_tv, viewGroup, false);
        return new CardViewTvAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewTvAdapter.CardViewViewHolder cardViewViewHolder, int i) {
        final TvItem tvItem = new TvItem();
        final String title = listTv.get(i).getName();
        tvItem.setId(listTv.get(i).getId());
        tvItem.setName(listTv.get(i).getName());
        tvItem.setPosterPath(listTv.get(i).getPosterPath());
        tvItem.setVoteAverage(listTv.get(i).getVoteAverage());
        tvItem.setOverview(listTv.get(i).getOverview());
        cardViewViewHolder.tvJudul.setText(listTv.get(i).getName());
        Glide.with(cardViewViewHolder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w154" + listTv.get(i).getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(cardViewViewHolder.imgPhoto);

        cardViewViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Clicked element " + title, Snackbar.LENGTH_LONG).show();
                Intent moveIntent = new Intent(context, DetailTvActivity.class);
                moveIntent.putExtra(DetailTvActivity.EXTRA_TV, tvItem);
                context.startActivity(moveIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvJudul;
        CardView cardView;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvJudul = itemView.findViewById(R.id.tv_item_name);
            cardView = itemView.findViewById(R.id.card_view_tv);
        }
    }
}

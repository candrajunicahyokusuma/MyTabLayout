package com.example.mytablayout.view.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mytablayout.R;
import com.example.mytablayout.database.AplicationDatabase;
import com.example.mytablayout.database.MovieDB;
import com.example.mytablayout.model.tv.TvItem;

import java.util.List;


public class DetailTvActivity extends AppCompatActivity {
    public static String EXTRA_TV ="extra_tv";
    TextView tvJudul, tvJT, tvOverview;
    ImageView imgPhoto;
    CheckBox likeIcon;
    TvItem tvItem;
    boolean favorite = false;
    private AplicationDatabase aplicationDatabase;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        tvJudul = findViewById(R.id.tv_item_judul);
        tvJT = findViewById(R.id.tv_item_vote_average);
        tvOverview = findViewById(R.id.tv_item_overview);
        imgPhoto = findViewById(R.id.img_item_photo);
        likeIcon = findViewById(R.id.likeIcon);
        tvItem = getIntent().getParcelableExtra(EXTRA_TV);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(tvItem.getName());
        tvJudul.setText(tvItem.getName());
        tvJT.setText(String.valueOf(tvItem.getVoteAverage()));
        tvOverview.setText(tvItem.getOverview());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w154"+tvItem.getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(imgPhoto);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        aplicationDatabase = AplicationDatabase.initDatabase(getApplicationContext());
        checkedFavorite();
        if (favorite == true){
            likeIcon.setChecked(true);
        }
        likeIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    saveFavorite();
                    favorite = true;
                    likeIcon.setChecked(true);
                }
                else{
                    deleteFavorite();
                    favorite = false;
                    likeIcon.setChecked(false);
                }
            }
        });

    }
    public void saveFavorite(){
        MovieDB movieDB = new MovieDB();
        movieDB.setId(tvItem.getId());
        movieDB.setTitle(tvItem.getName());
        movieDB.setOverview(tvItem.getOverview());
        movieDB.setPosterPath(tvItem.getPosterPath());
        movieDB.setReleaseDate("");
        movieDB.setVoteAvarage(tvItem.getVoteAverage());
        movieDB.setCategory("tv");
        aplicationDatabase.movieDAO().insert(movieDB);
    }
    public void  deleteFavorite(){
        MovieDB movieDB = new MovieDB();
        movieDB.setId(tvItem.getId());
        movieDB.setTitle(tvItem.getName());
        movieDB.setOverview(tvItem.getOverview());
        movieDB.setPosterPath(tvItem.getPosterPath());
        movieDB.setReleaseDate("");
        movieDB.setVoteAvarage(tvItem.getVoteAverage());
        movieDB.setCategory("tv");
        aplicationDatabase.movieDAO().delete(movieDB);
    }
    public void checkedFavorite(){
        List<MovieDB> getById = aplicationDatabase.movieDAO().getById(tvItem.getId());
        if (!getById.isEmpty()){
            favorite = true;

        }
    }
}

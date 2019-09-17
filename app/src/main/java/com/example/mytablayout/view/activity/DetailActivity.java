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
import com.example.mytablayout.database.AplicationDatabase;
import com.example.mytablayout.database.MovieDB;
import com.example.mytablayout.model.movie.ResultsItem;
import com.example.mytablayout.R;
import com.example.mytablayout.widget.FavoriteMTvWidget;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra";
    TextView tvJudul, tvRelease, tvOverview;
    boolean favorite = false;
    ImageView img_Photo;
    CheckBox likeIcon;
    ResultsItem listFilm ;
    private AplicationDatabase aplicationDatabase;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvJudul = findViewById(R.id.tv_judul);
        tvRelease = findViewById(R.id.tv_release);
        tvOverview = findViewById(R.id.tv_overview);
        img_Photo = findViewById(R.id.img_photo);
        likeIcon = findViewById(R.id.likeIcon);
        listFilm  = getIntent().getParcelableExtra(EXTRA_FILM);
        tvJudul.setText(listFilm.getTitle());
        tvRelease.setText(listFilm.getReleaseDate());
        tvOverview.setText(listFilm.getOverview());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w154"+listFilm.getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(img_Photo);
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
                    FavoriteMTvWidget favoriteMTvWidget = new FavoriteMTvWidget();

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
        movieDB.setId(listFilm.getId());
        movieDB.setTitle(listFilm.getTitle());
        movieDB.setOverview(listFilm.getOverview());
        movieDB.setPosterPath(listFilm.getPosterPath());
        movieDB.setReleaseDate(listFilm.getReleaseDate());
        movieDB.setVoteAvarage(0);
        movieDB.setCategory("movie");
        aplicationDatabase.movieDAO().insert(movieDB);
    }
    public void  deleteFavorite(){
        MovieDB movieDB = new MovieDB();
        movieDB.setId(listFilm.getId());
        movieDB.setTitle(listFilm.getTitle());
        movieDB.setOverview(listFilm.getOverview());
        movieDB.setPosterPath(listFilm.getPosterPath());
        movieDB.setReleaseDate(listFilm.getReleaseDate());
        movieDB.setVoteAvarage(0);
        movieDB.setCategory("movie");
        aplicationDatabase.movieDAO().delete(movieDB);
    }
    public void checkedFavorite(){
        List<MovieDB> getById = aplicationDatabase.movieDAO().getById(listFilm.getId());
        if (!getById.isEmpty()){
            favorite = true;

        }
    }
}

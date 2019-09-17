package com.example.mytablayout.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDAO {
    @Insert
    void insert(MovieDB movieDB);

    @Query("Select * from favorite_1")
    List<MovieDB> getMovieDB();

    @Query("Select * from favorite_1 where id = :idMovie limit 1")
    List<MovieDB> getById(int idMovie);

    @Query("Select * from favorite_1 where category = :category")
    List<MovieDB> getByCategory(String category);

    @Delete
    void delete(MovieDB movieDB);

}

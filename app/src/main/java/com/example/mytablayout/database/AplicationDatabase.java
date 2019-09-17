package com.example.mytablayout.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MovieDB.class}, version = 1, exportSchema = false)
public abstract class AplicationDatabase extends RoomDatabase {
    private static AplicationDatabase aplicationDatabase;

    public static AplicationDatabase initDatabase(Context context) {
        if (aplicationDatabase == null) {
            aplicationDatabase = Room.databaseBuilder(context, AplicationDatabase.class, "favorite").allowMainThreadQueries().build();
        }
        return aplicationDatabase;
    }

    public abstract MovieDAO movieDAO();
}

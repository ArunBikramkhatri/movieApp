package com.arun.movieapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.arun.movieapp.model.data.MovieData;

@Database(entities = {MovieData.class} , version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();
}

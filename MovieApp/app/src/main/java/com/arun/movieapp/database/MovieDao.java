package com.arun.movieapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.arun.movieapp.model.data.MovieData;


import java.util.List;

@Dao
public interface MovieDao {

    @Query("select * from MovieData")
    List<MovieData> getTopRatedMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<MovieData> movieData);


}

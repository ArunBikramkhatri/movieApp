package com.arun.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTrailerDataList {
    @SerializedName("results")
    private List<MovieTrailerData> movieTrailerData;

    public List<MovieTrailerData> getMovieTrailerData() {
        return movieTrailerData;
    }

    public void setMovieTrailerData(List<MovieTrailerData> movieTrailerData) {
        this.movieTrailerData = movieTrailerData;
    }
}

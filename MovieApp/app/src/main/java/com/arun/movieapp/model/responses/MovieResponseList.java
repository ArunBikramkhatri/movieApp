package com.arun.movieapp.model.responses;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponseList {

    private String TAG  = MovieResponseList.class.getSimpleName();

    private static MovieResponseList movieResponseList;
    @SerializedName("results")
    private List<MovieResponse> results;

    private List<MovieResponse> finalListOfMovies = null;


    public MovieResponseList() {
        Log.d(TAG, "object created ");
    }

    public List<MovieResponse> getFinalListOfMovies() {
        return finalListOfMovies;
    }


    public static MovieResponseList getInstance(){
        if(movieResponseList == null){
            movieResponseList = new MovieResponseList();
        }
        return movieResponseList;
    }
    public List<MovieResponse> getResponses() {
        return results;
    }

    public void setResponses(List<MovieResponse> responses) {
        this.results = results;
    }

    public void appendAndRetunMovieList(List<MovieResponse> movieList){
        if(finalListOfMovies == null){

            finalListOfMovies = new ArrayList<>();
        }
        if(movieList != null){
            finalListOfMovies.addAll(movieList);
        }

    }


}
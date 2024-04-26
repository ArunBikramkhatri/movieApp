package com.arun.movieapp.api;
import okhttp3.Callback;

public interface MovieAPI {

    void getMovieDetailsByID(int id , Callback callback);

    void getTopRatedMovies(Callback callback);

    void getVideoLinkFromMovieId(String movieId ,Callback callback);

}

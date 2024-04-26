package com.arun.movieapp.service;

import android.util.Log;

import com.arun.movieapp.api.MovieAPI;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MovieAPIBuilder implements MovieAPI {

    private static final String TAG = MovieAPIBuilder.class.getSimpleName();
    private String URL = "https://api.themoviedb.org/3/movie/";
    private String language = "?language=en-US";
    private int pageNo = 1;
    private String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNDRkMWZhZGFhN2JhMDdlNTQzNTZmY2JmNDA3ODExZSIsInN1YiI6IjYzODFjM2FhZmI4MzQ2MDA4NDNmZjZlZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7bEz0nNg3LPjvKunq0o1aPVQAoTBkm1TED-VRdaueFc";
    private OkHttpClient client;

    private static MovieAPIBuilder movieAPIBuilder;


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public MovieAPIBuilder() {
        Log.d(TAG, "new object created ");
        client = new OkHttpClient();
    }

    public static MovieAPIBuilder getInstance() {
        if (movieAPIBuilder == null) {
            movieAPIBuilder = new MovieAPIBuilder();
        }
        return movieAPIBuilder;
    }

    private <T> Request requestBuilder(T data) {
        try {
            Log.d(TAG, String.valueOf(pageNo));
            Request request = new Request.Builder()
                    .url(URL + String.valueOf(data) + language + "&page=" + String.valueOf(getPageNo()))
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", token)
                    .build();
            return request;
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getMovieDetailsByID(int id, Callback callback) {
        Request request = requestBuilder(id);
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void getTopRatedMovies(Callback callback) {
        Request request = requestBuilder("top_rated");
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void getVideoLinkFromMovieId(String movieId, Callback callback) {
        Request request = new Request.Builder().url("https://api.themoviedb.org/3/movie/" + String.valueOf(movieId) + "/videos")
                .get()
                .addHeader("Authorization", token)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(callback);
    }

}

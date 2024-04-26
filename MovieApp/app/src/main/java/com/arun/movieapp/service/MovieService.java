package com.arun.movieapp.service;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.arun.movieapp.listeners.MovieDataFetchListener;
import com.arun.movieapp.model.MovieResponse;
import com.arun.movieapp.model.MovieResponseList;
import com.arun.movieapp.model.MovieTrailerData;
import com.arun.movieapp.model.MovieTrailerDataList;
import com.arun.movieapp.ui.MainActivity;
import com.arun.movieapp.ui.recyclerView.MovieRecyclerViewAdapter;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieService {
    private static final String TAG = "MovieService";
    private MovieAPIBuilder movieAPIBuilder;
    private MovieTrailerDataList movieTrailerDataList;
    private String movieYoutubeTrailerId;
    private Gson gson;

    private MovieRecyclerViewAdapter recyclerViewAdapter;
    private MovieResponseList movieResponseList;
    private MovieDataFetchListener movieDataFetchListener;

    private boolean isLoading = false;

    public MovieService() {
        movieAPIBuilder = new MovieAPIBuilder();

        movieTrailerDataList = new MovieTrailerDataList();
        gson = new Gson();
    }

    public void setMovieDataFetchListener(MovieDataFetchListener movieDataFetchListener) {
        this.movieDataFetchListener = movieDataFetchListener;
    }

    public void fetchTopRatedMovies() {
        movieAPIBuilder.getTopRatedMovies(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                logException(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    movieResponseList = new Gson().fromJson(responseString, MovieResponseList.class);
                    Log.d(TAG, "onResponse: " + movieResponseList.getResponses().toString());
                    for (MovieResponse movieResponse : movieResponseList.getResponses()) {
                        movieResponse.setImageLink(movieResponse.getImageLink());
                        movieResponse.setBackDropImage(movieResponse.getBackDropImage());
                    }
                    isLoading = false;

                    recyclerViewAdapter.addMoviesList(movieResponseList.getResponses());
                } else {
                    Log.d(TAG, response.code() + " " + response.message());
                }
            }
        });
    }

    void logException(Exception e) {
        Log.e(TAG, e.getLocalizedMessage(), e);
        e.printStackTrace();
    }


    void fetchNextPage() {
        Log.d(TAG, "fetch next page");
        int pageNo = movieAPIBuilder.getPageNo();
        pageNo++;
        movieAPIBuilder.setPageNo(pageNo);
        fetchTopRatedMovies();
    }


    public void fetchMovieYoutubeTrailerId(String movieId) {
        Log.d(TAG, "fetch movie youtube" + movieId);
        movieAPIBuilder.getVideoLinkFromMovieId(movieId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(TAG, "youtube trailer id success");
                    String responseString = response.body().string();
                    movieTrailerDataList = gson.fromJson(responseString, MovieTrailerDataList.class);

                    for (MovieTrailerData movieTrailerData : movieTrailerDataList.getMovieTrailerData()) {
                        if (movieTrailerData.getVideoType().equals("Trailer")) {
                            Log.d(TAG, "trailer id " + movieTrailerData.getYoutubeTrailerId());
                            movieYoutubeTrailerId = movieTrailerData.getYoutubeTrailerId();
                            movieDataFetchListener.onMovieYoutubeTrailerIdFetched(movieYoutubeTrailerId);
                            return;
                        }
                    }
                }
            }
        });

    }

    void loadMore() {
        fetchNextPage();
    }
}

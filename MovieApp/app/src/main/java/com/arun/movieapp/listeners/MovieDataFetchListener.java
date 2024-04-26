package com.arun.movieapp.listeners;

public interface MovieDataFetchListener {
    void onTopRatedMoveDataFetched();

    void onMovieYoutubeTrailerIdFetched(String id);
}

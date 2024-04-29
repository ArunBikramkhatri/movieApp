package com.arun.movieapp.dto;

import com.arun.movieapp.model.data.MovieData;
import com.arun.movieapp.model.responses.MovieResponse;

import java.util.ArrayList;
import java.util.List;

public class MovieDTO {


    public static MovieData responseToMovieData(MovieResponse movieResponse) {
        MovieData movieData = new MovieData.Builder().movieName(movieResponse.getMovieName())
                .movieId(movieResponse.getMovieId())
                .description(movieResponse.getDescription())
                .rating(movieResponse.getRating())
                .imageLink(movieResponse.getImageLink())
                .backDropImage(movieResponse.getBackDropImage()).build();
        return movieData;
    }


    public static MovieResponse movieDataToResponse(MovieData movieData) {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setMovieId(movieData.getMovieId());
        movieResponse.setMovieName(movieData.getMovieName());
        movieResponse.setRating(movieData.getRating());
        movieResponse.setDescription(movieData.getDescription());
        movieResponse.setImageLink(movieData.getImageLink());
        movieResponse.setBackDropImage("https://image.tmdb.org/t/p/original" + movieData.getBackDropImage());
        return movieResponse;
    }

    public static List<MovieResponse> mapToMovieResponseList(List<MovieData> movieDataList) {
        List<MovieResponse> movieResponseList = new ArrayList<>();
        for (MovieData movieData : movieDataList) {
            MovieResponse movieResponse = MovieDTO.movieDataToResponse(movieData);
            movieResponseList.add(movieResponse);
        }
        return movieResponseList;
    }


    public static List<MovieData> mapToMovieDataList(List<MovieResponse> movieResponseList) {
        List<MovieData> movieDataList = new ArrayList<>();
        for (MovieResponse movieResponse : movieResponseList) {
            MovieData movieData = MovieDTO.responseToMovieData(movieResponse);
            movieDataList.add(movieData);
        }
        return movieDataList;
    }
}

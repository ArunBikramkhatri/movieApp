package com.arun.movieapp.model;

import com.google.gson.annotations.SerializedName;

public class MovieResponse {
    @SerializedName("id")
    private String movieId;
    @SerializedName("original_title")
    private String movieName ;

    @SerializedName("vote_average")
    private String rating  ;

    @SerializedName("overview")
    private String description ;

    @SerializedName("poster_path")
    private String imageLink;

    @SerializedName("backdrop_path")
    private String backDropImage;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getDescription() {
        return description;
    }

    public String getBackDropImage() {
        return backDropImage;
    }

    public void setBackDropImage(String backDropImage) {
        this.backDropImage = "https://image.tmdb.org/t/p/original" +  backDropImage;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRating() {
        return rating;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = "https://image.tmdb.org/t/p/original"+ imageLink;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movieName='" + movieName + '\'' +
                ", rating='" + rating + '\'' +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }
}

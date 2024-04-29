package com.arun.movieapp.model.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class MovieData {

    @PrimaryKey
    @NonNull
    private String movieId;
    @ColumnInfo(name = "movie_name")
    private String movieName ;
    @ColumnInfo(name = "rating")
    private String rating  ;
    @ColumnInfo(name = "description")
    private String description ;

    @ColumnInfo(name = "poster_link")
    private String imageLink;

    @ColumnInfo(name = "backdrop_image_link")
    private String backDropImage;

    public MovieData() {
    }

    public static class Builder {
        private String movieId;
        private String movieName;
        private String rating;
        private String description;
        private String imageLink;
        private String backDropImage;

        public Builder() {
            // Default constructor
        }

        public Builder movieId(String movieId) {
            this.movieId = movieId;
            return this;
        }

        public Builder movieName(String movieName) {
            this.movieName = movieName;
            return this;
        }

        public Builder rating(String rating) {
            this.rating = rating;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder imageLink(String imageLink) {
            this.imageLink = imageLink;
            return this;
        }

        public Builder backDropImage(String backDropImage) {
            this.backDropImage = backDropImage;
            return this;
        }

        public MovieData build() {
            return new  MovieData(this);
        }
    }

    // Constructor using Builder
    private MovieData(Builder builder) {
        this.movieId = builder.movieId;
        this.movieName = builder.movieName;
        this.rating = builder.rating;
        this.description = builder.description;
        this.imageLink = builder.imageLink;
        this.backDropImage = builder.backDropImage;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
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

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getBackDropImage() {
        return backDropImage;
    }

    public void setBackDropImage(String backDropImage) {
        this.backDropImage = backDropImage;
    }
}

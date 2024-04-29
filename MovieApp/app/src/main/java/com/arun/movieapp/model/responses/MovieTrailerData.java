package com.arun.movieapp.model.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTrailerData {

    @SerializedName("type")
    private String videoType ; //i.e trailer or review or..
    @SerializedName("key")
    private String youtubeTrailerId ;

    public MovieTrailerData() {
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getYoutubeTrailerId() {
        return youtubeTrailerId;
    }

    public void setYoutubeTrailerId(String youtubeTrailerId) {
        this.youtubeTrailerId = youtubeTrailerId;
    }

    @Override
    public String toString() {
        return "MovieTrailerData{" +
                "videoType='" + videoType + '\'' +
                ", youtubeTrailerId='" + youtubeTrailerId + '\'' +
                '}';
    }
}

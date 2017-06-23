package com.tmbdnews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopRated {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("original_language")

    public Float getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Integer getId() {
        return id;
    }
}

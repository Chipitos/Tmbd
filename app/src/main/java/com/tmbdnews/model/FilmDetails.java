package com.tmbdnews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tmbdnews.server.response.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class FilmDetails extends BaseResponse{

    @SerializedName("genres")
    @Expose
    private final List<Genre> genres = new ArrayList<>();
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getOverview() {
        return overview;
    }

    public class Genre {
        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }
    }
}

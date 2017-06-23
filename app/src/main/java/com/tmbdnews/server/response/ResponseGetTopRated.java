package com.tmbdnews.server.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tmbdnews.model.TopRated;

import java.util.List;

public class ResponseGetTopRated extends BaseResponse {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("results")
    @Expose
    private List<TopRated> results;

    public Integer getPage() {
        return page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<TopRated> getResults() {
        return results;
    }
}

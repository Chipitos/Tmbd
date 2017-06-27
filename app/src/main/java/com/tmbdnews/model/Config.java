package com.tmbdnews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {

    @SerializedName("base_url")
    @Expose
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

}

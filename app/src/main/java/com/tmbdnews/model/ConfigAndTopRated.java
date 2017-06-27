package com.tmbdnews.model;

import com.tmbdnews.server.response.BaseResponse;

import java.util.List;

public class ConfigAndTopRated extends BaseResponse {
    private List<TopRated> topRated;
    private Config config;

    public ConfigAndTopRated(Config config,List<TopRated> topRated) {
        this.topRated = topRated;
        this.config = config;
    }

    public List<TopRated> getTopRated() {
        return topRated;
    }

    public Config getConfig() {
        return config;
    }
}

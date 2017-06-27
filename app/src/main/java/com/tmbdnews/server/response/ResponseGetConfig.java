package com.tmbdnews.server.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tmbdnews.model.Config;

public class ResponseGetConfig extends BaseResponse {

    @SerializedName("images")
    @Expose
    private Config config;

    public Config getConfig() {
        return config;
    }
}

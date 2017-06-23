package com.tmbdnews.server.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tmbdnews.model.Images;

public class ResponseGetConfig extends BaseResponse {

    @SerializedName("images")
    @Expose
    private Images images;

    public Images getImages() {
        return images;
    }
}

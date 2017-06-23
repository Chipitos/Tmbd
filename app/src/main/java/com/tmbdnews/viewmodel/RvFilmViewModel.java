package com.tmbdnews.viewmodel;

import android.databinding.Bindable;

import com.tmbdnews.R;
import com.tmbdnews.model.TopRated;
import com.tmbdnews.utils.NetworkUtils;

public class RvFilmViewModel extends BaseViewModel<TopRated> {
    private final static String IMAGE_SIZE = "/w342/";

    public void init(TopRated item) {
        this.item = item;
        notifyChange();
    }

    @Bindable
    public String getImageUrl() {
        return NetworkUtils.getBaseUrl() + IMAGE_SIZE + item.getPosterPath();
    }

    @Bindable
    public String getTitle() {
        return item.getTitle();
    }

    @Bindable
    public String getRating() {
        return resUtils.getString(R.string.rating) + String.valueOf(item.getVoteAverage());
    }
}

package com.tmbdnews.viewmodel;

import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import com.tmbdnews.model.FilmDetails;
import com.tmbdnews.utils.NetworkUtils;

public class FrgFilmDetailsViewModel extends BaseViewModel<FilmDetails> {
    private final static String IMAGE_SIZE = "/original/";
    public final ObservableBoolean isShowEmptyView = new ObservableBoolean(false);

    public FrgFilmDetailsViewModel() {
        item = new FilmDetails();
    }

    public void init(FilmDetails item) {
        this.item = item;
        notifyChange();
    }

    @Bindable
    public String getTitle() {
        return item.getTitle();
    }

    @Bindable
    public String getOverview() {
        return item.getOverview();
    }

    @Bindable
    public String getImageUrl() {
        return networkUtils.getBaseUrl() + IMAGE_SIZE + item.getPosterPath();
    }


    @Bindable
    public String getGenresAndYears() {
        if (item.getReleaseDate() == null)
            return "";
        String result = item.getReleaseDate() + ", ";
        for (FilmDetails.Genre genre : item.getGenres()) {
            result += genre.getName() + ", ";
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }


}

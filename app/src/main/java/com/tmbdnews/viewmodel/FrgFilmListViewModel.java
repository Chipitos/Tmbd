package com.tmbdnews.viewmodel;

import android.databinding.ObservableBoolean;

public class FrgFilmListViewModel extends BaseInjectViewModel {

    public final ObservableBoolean isProgressVisible = new ObservableBoolean(false);

    public final ObservableBoolean isShowEmptyView = new ObservableBoolean(false);
}

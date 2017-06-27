package com.tmbdnews.viewmodel;

import android.databinding.BaseObservable;

import com.tmbdnews.App;
import com.tmbdnews.utils.NetworkUtils;
import com.tmbdnews.utils.ResUtils;

import javax.inject.Inject;


public abstract class BaseInjectViewModel extends BaseObservable {
    @Inject
    ResUtils resUtils;

    @Inject
    NetworkUtils networkUtils;

    BaseInjectViewModel() {
        App.getComponent().inject(this);
    }
}

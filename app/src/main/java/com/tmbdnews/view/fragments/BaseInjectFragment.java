package com.tmbdnews.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.tmbdnews.server.IRetrofitService;
import com.tmbdnews.utils.NetworkUtils;
import com.tmbdnews.utils.SharedPreferenceUtils;
import com.tmbdnews.view.activities.BaseInjectActivity;
import com.tmbdnews.view.navigation.Navigator;

import javax.inject.Inject;


public abstract class BaseInjectFragment extends Fragment {
    @Inject
    protected IRetrofitService service;

    @Inject
    Navigator navigator;

    @Inject
    protected NetworkUtils networkUtils;


    @Inject
    protected SharedPreferenceUtils utils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseInjectActivity activity = (BaseInjectActivity) getActivity();
        activity.buildComponent().inject(this);
        setRetainInstance(true);
    }
}

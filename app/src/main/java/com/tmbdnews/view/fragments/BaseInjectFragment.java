package com.tmbdnews.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmbdnews.server.IRetrofitService;
import com.tmbdnews.utils.NetworkUtils;
import com.tmbdnews.utils.SharedPreferenceUtils;
import com.tmbdnews.view.activities.BaseInjectActivity;
import com.tmbdnews.view.navigation.Navigator;

import javax.inject.Inject;


public abstract class BaseInjectFragment extends Fragment {
    @Inject
    protected IRetrofitService service;

    protected Navigator navigator;

    @Inject
    protected NetworkUtils networkUtils;


    @Inject
    protected SharedPreferenceUtils utils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        BaseInjectActivity activity = (BaseInjectActivity) getActivity();
        activity.buildComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BaseInjectActivity activity = (BaseInjectActivity) getActivity();
        navigator = activity.getNavigator();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

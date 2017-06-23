package com.tmbdnews.view.activities;

import android.os.Bundle;

import com.tmbdnews.R;
import com.tmbdnews.databinding.ActMainBinding;
import com.tmbdnews.server.response.BaseResponse;
import com.tmbdnews.server.response.ResponseGetConfig;
import com.tmbdnews.utils.SharedPreferenceUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ActMain extends BaseBindingActivity<ActMainBinding> {

    @Override
    protected int initLayout() {
        return R.layout.act_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getConfig();
        navigator.showFrgFilmsList();
    }

    private void handleResponse(BaseResponse response) {
        if (response instanceof ResponseGetConfig) {
            ResponseGetConfig responseGetConfig = (ResponseGetConfig) response;
            SharedPreferenceUtils.saveString(SharedPreferenceUtils.BASE_URL, responseGetConfig.getImages().getBaseUrl());
        }
    }

    public void getConfig() {
        Observable<ResponseGetConfig> bb = service.getConfig();
        bb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse);
    }


}

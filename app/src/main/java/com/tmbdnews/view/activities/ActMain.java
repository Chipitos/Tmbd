package com.tmbdnews.view.activities;

import android.os.Bundle;

import com.tmbdnews.R;
import com.tmbdnews.databinding.ActMainBinding;

public class ActMain extends BaseBindingActivity<ActMainBinding> {

    @Override
    protected int initLayout() {
        return R.layout.act_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator.showFrgFilmsList();
    }

}

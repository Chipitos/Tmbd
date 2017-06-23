package com.tmbdnews.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tmbdnews.App;
import com.tmbdnews.di.component.ActivityComponent;
import com.tmbdnews.di.component.DaggerActivityComponent;
import com.tmbdnews.di.module.ActivityModule;
import com.tmbdnews.server.IRetrofitService;
import com.tmbdnews.view.navigation.Navigator;

import javax.inject.Inject;


public abstract class BaseInjectActivity extends AppCompatActivity {
    @Inject
    protected IRetrofitService service;
    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponent().inject(this);
    }

    public ActivityComponent buildComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}



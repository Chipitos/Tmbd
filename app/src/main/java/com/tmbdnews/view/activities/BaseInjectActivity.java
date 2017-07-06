package com.tmbdnews.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.tmbdnews.App;
import com.tmbdnews.di.component.ActivityComponent;
import com.tmbdnews.di.component.DaggerActivityComponent;
import com.tmbdnews.di.module.ActivityModule;
import com.tmbdnews.view.navigation.Navigator;

import javax.inject.Inject;


public abstract class BaseInjectActivity extends FragmentActivity {
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

    public Navigator getNavigator() {
        return this.navigator;
    }
}



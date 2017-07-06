package com.tmbdnews.di.module;

import android.support.v4.app.FragmentManager;

import com.tmbdnews.view.activities.BaseInjectActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final BaseInjectActivity activity;

    public ActivityModule(BaseInjectActivity activity) {
        this.activity = activity;
    }

    @Provides
    BaseInjectActivity provideActivity() {
        return activity;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return activity.getSupportFragmentManager();
    }
}

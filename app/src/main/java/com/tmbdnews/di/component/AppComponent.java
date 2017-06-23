package com.tmbdnews.di.component;


import android.app.Application;
import android.content.Context;

import com.tmbdnews.di.module.AppModule;
import com.tmbdnews.utils.ResUtils;
import com.tmbdnews.viewmodel.BaseInjectViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)

public interface AppComponent {

    void inject(Application app);

    void inject(BaseInjectViewModel viewModel);

    Context getContext();

    ResUtils getResUtils();
}

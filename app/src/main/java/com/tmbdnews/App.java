package com.tmbdnews;

import android.app.Application;

import com.tmbdnews.di.component.AppComponent;
import com.tmbdnews.di.component.DaggerAppComponent;
import com.tmbdnews.di.module.AppModule;

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
        component.inject(this);
        System.out.println("test");
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

}

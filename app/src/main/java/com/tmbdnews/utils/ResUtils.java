package com.tmbdnews.utils;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResUtils {
    private final Context context;

    @Inject
    ResUtils(Context context) {
        this.context = context;
    }

    public String getString(int id) {
        return context.getResources().getString(id);
    }

}

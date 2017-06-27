package com.tmbdnews.di.module;

import android.content.Context;

import com.tmbdnews.utils.NetworkUtils;
import com.tmbdnews.utils.ResUtils;
import com.tmbdnews.utils.SharedPreferenceUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    ResUtils provideResUtils(Context context) {
        return new ResUtils(context);
    }

    @Provides
    SharedPreferenceUtils providePreferenceUtils(Context context) {
        return new SharedPreferenceUtils(context);
    }

    @Provides
    NetworkUtils provideNetworkUtils(Context context, SharedPreferenceUtils utils) {
        return new NetworkUtils(context, utils);
    }


}

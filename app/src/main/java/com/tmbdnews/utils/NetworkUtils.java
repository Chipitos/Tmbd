package com.tmbdnews.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.tmbdnews.App;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NetworkUtils {
    private Context context;
    private SharedPreferenceUtils utils;

    @Inject
    public NetworkUtils(Context context, SharedPreferenceUtils utils){
        this.context = context;
        this.utils = utils;
    }

    public boolean isNetworkAvailable() {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public String getBaseUrl() {
        return utils.readString(SharedPreferenceUtils.BASE_URL);
    }
}

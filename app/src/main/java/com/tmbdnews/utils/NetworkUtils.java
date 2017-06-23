package com.tmbdnews.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.tmbdnews.App;

public class NetworkUtils {

    public static boolean isNetworkAvailable() {
        return ((ConnectivityManager) App.getComponent().getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static String getBaseUrl() {
        return SharedPreferenceUtils.readString(SharedPreferenceUtils.BASE_URL);
    }
}

package com.tmbdnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tmbdnews.App;


public class SharedPreferenceUtils {
    public static final String BASE_URL = "baseUrl";
    private static final String PREF_NAME = "TESTPreferences";

    public static void saveString(String key, String value) {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    static String readString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    private static SharedPreferences getSharedPreferences() {
        return App.getComponent().getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

}

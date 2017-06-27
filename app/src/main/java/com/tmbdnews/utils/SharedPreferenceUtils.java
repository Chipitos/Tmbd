package com.tmbdnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tmbdnews.annotations.PerActivity;

import javax.inject.Inject;

@PerActivity
public class SharedPreferenceUtils {
    private Context context;
    public static final String BASE_URL = "baseUrl";
    private static final String PREF_NAME = "TESTPreferences";

    @Inject
    public SharedPreferenceUtils(Context context){
        this.context = context;
    }

    public  void saveString(String key, String value) {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String readString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

}

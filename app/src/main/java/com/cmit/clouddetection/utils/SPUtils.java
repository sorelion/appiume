package com.cmit.clouddetection.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pact on 2018/10/15.
 */

public class SPUtils {
    private static String RECORDING = "cloudDetection";

    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(RECORDING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(RECORDING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(RECORDING, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(RECORDING, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }
}

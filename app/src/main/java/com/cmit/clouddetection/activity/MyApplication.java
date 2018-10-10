package com.cmit.clouddetection.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by pact on 2018/10/9.
 */

public class MyApplication extends Application {
    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();
    }

    public static Context getContext() {
        return CONTEXT;
    }
}

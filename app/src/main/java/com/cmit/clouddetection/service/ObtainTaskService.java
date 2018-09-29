package com.cmit.clouddetection.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cmit.clouddetection.threadpool.ObtainTaskRunnable;
import com.cmit.clouddetection.threadpool.ThreadPools;

public class ObtainTaskService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadPools.excute(new ObtainTaskRunnable(this));
    }
}

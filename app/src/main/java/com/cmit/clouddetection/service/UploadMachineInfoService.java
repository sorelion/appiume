package com.cmit.clouddetection.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cmit.clouddetection.threadpool.ThreadPools;
import com.cmit.clouddetection.threadpool.UploadMachineInfoRunnable;

/**
 * Created by pact on 2018/9/27.
 */

public class UploadMachineInfoService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadPools.excute(new UploadMachineInfoRunnable(this));
    }
}

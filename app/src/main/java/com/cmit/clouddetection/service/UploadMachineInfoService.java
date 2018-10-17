package com.cmit.clouddetection.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.cmit.clouddetection.contstant.SPContsant;
import com.cmit.clouddetection.threadpool.ThreadPools;
import com.cmit.clouddetection.threadpool.UploadMachineInfoRunnable;
import com.cmit.clouddetection.utils.SPUtils;

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
        if (!"".equals(SPUtils.getString(this, SPContsant.RESOURCE_ID, ""))) {
            ThreadPools.excute(new UploadMachineInfoRunnable(this));
        }
    }
}

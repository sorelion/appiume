package com.cmit.clouddetection.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.cmit.clouddetection.bean.TaskInfo;
import com.cmit.clouddetection.contstant.Constants;
import com.cmit.clouddetection.threadpool.AppWorkRunnable;
import com.cmit.clouddetection.threadpool.ObtainTaskRunnable;
import com.cmit.clouddetection.threadpool.ThreadPools;

public class ObtainTaskService extends Service {

    public static final int WHAT_GETTASK = 101;//请求任务标示(重复执行)
    public static final int START_WORK_APP = 102;//开始执行脚本
    private int taskMode;//任务频率   private int taskMode;//任务频率
    private boolean isAutoLogin = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_WORK_APP:
                    TaskInfo taskInfo = (TaskInfo) msg.obj;
                    ThreadPools.excute(new AppWorkRunnable(ObtainTaskService.this, isAutoLogin, taskInfo));
                    break;
                case WHAT_GETTASK:
                    //取任务线程
                    ThreadPools.excute(new ObtainTaskRunnable(ObtainTaskService.this,mHandler, taskMode, WHAT_GETTASK));
                    break;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new taskFrequencyController();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        taskMode = Constants.GETTESTTASKRATE_NORMAL;
        //取任务线程
        ThreadPools.excute(new ObtainTaskRunnable(ObtainTaskService.this,mHandler, taskMode, WHAT_GETTASK));
    }

    public class taskFrequencyController extends Binder implements changeTaskFrequency {
        @Override
        public void changeTaskFrequency(int mode) {
            // 当任务频率模式发生更改的话，就取消任务，以新的频率提交定时任务
            taskMode = mode;
            mHandler.removeMessages(WHAT_GETTASK);
            ThreadPools.excute(new ObtainTaskRunnable(ObtainTaskService.this,mHandler, taskMode, WHAT_GETTASK));
        }
    }

    public interface changeTaskFrequency {
        void changeTaskFrequency(int taskMode);
    }
}

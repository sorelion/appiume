package com.cmit.clouddetection.threadpool;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cmit.clouddetection.bean.TaskInfo;
import com.cmit.clouddetection.contstant.HttpContstant;
import com.cmit.clouddetection.request.MachineInfoService;
import com.cmit.clouddetection.service.ObtainTaskService;
import com.cmit.clouddetection.utils.SystemUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pact on 2018/9/29.
 */

public class ObtainTaskRunnable implements Runnable {
    private int taskMode;
    private int what;
    private Handler handler;

    public ObtainTaskRunnable(Handler handler, int taskMode, int what) {
        this.handler = handler;
        this.taskMode = taskMode;
        this.what = what;
    }

    @Override
    public void run() {
        if (ThreadPools.iswork) {
            try {
                synchronized (ThreadPools.getThreadLock()) {
                    ThreadPools.getThreadLock().wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        getData();
        //延时发送信息
        handler.sendEmptyMessageDelayed(what, taskMode);
    }

    private void getData() {
        Log.i("sore","执行了");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpContstant.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MachineInfoService machineInfoService = retrofit.create(MachineInfoService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("imei", SystemUtils.getImei());
        machineInfoService.getTaskTest(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TaskInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TaskInfo responeData) {
                        Message message = handler.obtainMessage();
                        message.obj=responeData;
                        message.what= ObtainTaskService.START_WORK_APP;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("sore", "onError");
                        Log.i("sore", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("sore", "onComplete");
                    }
                });
    }
}

package com.cmit.clouddetection.threadpool;

import android.content.Context;
import android.util.Log;

import com.cmit.clouddetection.bean.RequestData;
import com.cmit.clouddetection.bean.ResponeData;
import com.cmit.clouddetection.contstant.HttpContstant;
import com.cmit.clouddetection.request.MachineInfoService;
import com.cmit.clouddetection.utils.AES;
import com.cmit.clouddetection.utils.SystemUtils;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private Context mContext;

    public ObtainTaskRunnable(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpContstant.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MachineInfoService machineInfoService = retrofit.create(MachineInfoService.class);

        RequestData requestData = new RequestData();
        HashMap<String, String> map = new HashMap<>();
        map.put("imsi", SystemUtils.getImsi(mContext));
        map.put("imei", SystemUtils.getImei());
        requestData.setData(map);
        HashMap<String, String> maps = new HashMap<>();
        SimpleDateFormat sdf_key = new SimpleDateFormat("yyyyMMddHHmmss");
        maps.put("data", new Gson().toJson(requestData));
        maps.put("time", sdf_key.format(new Date()));
        maps.put("key", encodeIMEI(sdf_key.format(new Date()), SystemUtils.getImei()));
        machineInfoService.getTaskTest(maps)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("sore", "onSubscribe");
                    }

                    @Override
                    public void onNext(String responeData) {
                        Log.i("sore", "onNext");
                        Log.i("sore", responeData);
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

    /**
     * 加密手机串号，供网络请求探测任务使用
     */
    public static String encodeIMEI(String time, String imei) {
        String result = "";
        try {
            String keyF = AES.Encrypt(time, time + "ok").substring(0, 16);
            String keyS = keyF.substring(0, 16);
            result = AES.Encrypt("", keyS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

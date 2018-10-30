package com.cmit.clouddetection.threadpool;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.cmit.clouddetection.bean.TaskInfo;
import com.cmit.clouddetection.contstant.HttpContstant;
import com.cmit.clouddetection.service.ObtainTaskService;
import com.cmit.clouddetection.utils.LogUtil;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;

import java.io.IOException;

/**
 * Created by pact on 2018/9/29.
 */

public class ObtainTaskRunnable implements Runnable {
    private int taskMode;
    private int what;
    private Handler handler;
    private Context mContext;

    public ObtainTaskRunnable(Context context, Handler handler, int taskMode, int what) {
        this.handler = handler;
        this.taskMode = taskMode;
        this.what = what;
        this.mContext = context;
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

        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //延时发送信息
        handler.sendEmptyMessageDelayed(what, taskMode);
    }

    private void getData() throws IOException {
        StringRequest stringRequest = new StringRequest(HttpContstant.URL + HttpContstant.GETTASK, RequestMethod.POST);
        stringRequest.setDefineRequestBodyForJson("{\"imei\":\"1234ACD\"}");
        NoHttp.newRequestQueue().add(0, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }
            @Override
            public void onSucceed(int what, Response<String> response) {
                JSONObject result = JSONObject.parseObject(response.get());
                int code = (Integer) result.get("code");
                if (code == 0) {//有数据
                    TaskInfo taskInfo = new Gson().fromJson(response.get(), TaskInfo.class);
                    Message message = handler.obtainMessage();
                    message.obj = taskInfo;
                    message.what = ObtainTaskService.START_WORK_APP;
                    handler.sendMessage(message);
                    //没有任务
                } else {
                    LogUtil.getInstance().increaseLog((String) result.get("msg"), null);
                }
                Log.i("sore", "onSucceed");
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i("sore", "onFailed");
            }

            @Override
            public void onFinish(int what) {
                Log.i("sore", "onFinish");
            }
        });


    }


}

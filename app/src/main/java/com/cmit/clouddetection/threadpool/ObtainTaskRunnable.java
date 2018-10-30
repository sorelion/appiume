package com.cmit.clouddetection.threadpool;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.cmit.clouddetection.contstant.HttpContstant;
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

    public ObtainTaskRunnable(Context context,Handler handler, int taskMode, int what) {
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
        NoHttp.initialize(mContext);
//        AssetManager am = mContext.getResources().getAssets();
//        InputStream is = am.open("test.png");
//        Bitmap image = BitmapFactory.decodeStream(is);
//        StringRequest stringRequest = (StringRequest) new StringRequest(HttpContstant.GETTASK, RequestMethod.POST).set("file", JSON.toJSONString(image));
//        NoHttp.newRequestQueue().add(0, stringRequest, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//                Log.i("sore", "" + what);
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Log.i("sore", "onSucceed");
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                Log.i("sore", "onFailed");
//            }
//
//            @Override
//            public void onFinish(int what) {
//                Log.i("sore", "onFinish");
//            }
//        });

        Log.i("sore", "执行了");
//        String data = "{\n" +
//                "    \"code\": 0,\n" +
//                "    \"msg\": \"操作成功!\",\n" +
//                "    \"data\": {\n" +
//                "        \"id\": 1,\n" +
//                "        \"instanceName\": \"2018-10-17 12:00:00\",\n" +
//                "        \"taskStatus\": 1,\n" +
//                "        \"terminalResourceMark\": null,\n" +
//                "        \"phoneNum\": null,\n" +
//                "        \"taskId\": 1,\n" +
//                "        \"scriptId\": 1,\n" +
//                "        \"strategyId\": 1,\n" +
//                "        \"taskSerial\": 1540281098753,\n" +
//                "        \"operator\": 1,\n" +
//                "        \"provinceName\": \"广东\",\n" +
//                "        \"businessName\": \"流量查询\",\n" +
//                "        \"channel\": 1,\n" +
//                "        \"isAided\": null,\n" +
//                "        \"uselocalnum\": 0,\n" +
//                "        \"smsVerifycodeConfigs\": [],\n" +
//                "        \"scriptInfos\": [\n" +
//                "            {\n" +
//                "                \"id\": 1,\n" +
//                "                \"serialNum\": 1,\n" +
//                "                \"operateType\": 1,\n" +
//                "                \"paramValue\": \"ChinaMobile\",\n" +
//                "                \"successKeyword\": \"\",\n" +
//                "                \"isTimestamp\": null,\n" +
//                "                \"scriptId\": 1\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"id\": 2,\n" +
//                "                \"serialNum\": 2,\n" +
//                "                \"operateType\": 2,\n" +
//                "                \"paramValue\": \"我的\",\n" +
//                "                \"successKeyword\": \"\",\n" +
//                "                \"isTimestamp\": null,\n" +
//                "                \"scriptId\": 1\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"id\": 3,\n" +
//                "                \"serialNum\": 3,\n" +
//                "                \"operateType\": 2,\n" +
//                "                \"paramValue\": \"我的流量\",\n" +
//                "                \"successKeyword\": \"流量\",\n" +
//                "                \"isTimestamp\": null,\n" +
//                "                \"scriptId\": 1\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    }\n" +
//                "}";
//        TaskInfo taskInfo = new Gson().fromJson(data, TaskInfo.class);
//
//        Message message = handler.obtainMessage();
//        message.obj = taskInfo;
//        message.what = ObtainTaskService.START_WORK_APP;
//        handler.sendMessage(message);

        StringRequest stringRequest = (StringRequest) new StringRequest(HttpContstant.GETTASK, RequestMethod.POST).set("imei", "{'imei':'1234ACD'}");
        NoHttp.newRequestQueue().add(0, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                Log.i("sore", "" + what);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
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

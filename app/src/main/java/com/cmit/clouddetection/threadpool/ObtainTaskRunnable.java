package com.cmit.clouddetection.threadpool;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.cmit.clouddetection.activity.MyApplication;
import com.cmit.clouddetection.bean.TaskInfo;
import com.cmit.clouddetection.contstant.HttpContstant;
import com.cmit.clouddetection.dao.ScriptDao;
import com.cmit.clouddetection.dao.ScriptDetailsDao;
import com.cmit.clouddetection.entry.Script;
import com.cmit.clouddetection.entry.ScriptDetails;
import com.cmit.clouddetection.service.ObtainTaskService;
import com.cmit.clouddetection.utils.LogUtil;
import com.cmit.clouddetection.utils.SystemUtils;
import com.cmit.clouddetection.utils.TimerUtils;
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
        try {
            if (ThreadPools.iswork) {
                synchronized (ThreadPools.getThreadLock()) {
                    ThreadPools.getThreadLock().wait();
                }
            }
            getData();
            //延时发送信息
            handler.sendEmptyMessageDelayed(what, taskMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String str = "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"操作成功!\",\n" +
            "    \"data\": {\n" +
            "        \"id\": 1,\n" +
            "        \"instanceName\": \"2018-10-17 12:00:00\",\n" +
            "        \"phoneNum\": \"13733153625\",\n" +
            "        \"taskId\": 1,\n" +
            "        \"scriptId\": 1,\n" +
            "        \"strategyId\": 1,\n" +
            "        \"taskSerial\": 1540977921138,\n" +
            "        \"operator\": 1,\n" +
            "        \"provinceName\": \"河南\",\n" +
            "        \"businessName\": \"流量查询\",\n" +
            "        \"channel\": 1,\n" +
            "        \"isAided\": 1,\n" +
            "        \"useLocalNum\": 0,\n" +
            "        \"smsVerifycodeConfigs\": [\n" +
            "            {\n" +
            "                \"id\": 1,\n" +
            "                \"keyword\": \"短信验证码是：\",\n" +
            "                \"length\": 6\n" +
            "            }\n" +
            "        ],\n" +
            "        \"scriptInfos\": [\n" +
            "            {\n" +
            "                \"id\": 6,\n" +
            "                \"serialNum\": 0.5,\n" +
            "                \"operateType\": 2,\n" +
            "                \"paramValue\": \"com.greenpoint.android.mc10086.activity\",\n" +
            "                \"successKeyword\": null,\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 1,\n" +
            "                \"serialNum\": 1,\n" +
            "                \"operateType\": 1,\n" +
            "                \"paramValue\": \"com.greenpoint.android.mc10086.activity/com.leadeon.cmcc.base.StartPageActivity\",\n" +
            "                \"successKeyword\": \"\",\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 5,\n" +
            "                \"serialNum\": 1.1,\n" +
            "                \"operateType\": 10,\n" +
            "                \"paramValue\": \"5\",\n" +
            "                \"successKeyword\": \"\",\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 13,\n" +
            "                \"serialNum\": 1.2,\n" +
            "                \"operateType\": 6,\n" +
            "                \"paramValue\": null,\n" +
            "                \"successKeyword\": null,\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 2,\n" +
            "                \"serialNum\": 2,\n" +
            "                \"operateType\": 3,\n" +
            "                \"paramValue\": \"我的\",\n" +
            "                \"successKeyword\": \"\",\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 3,\n" +
            "                \"serialNum\": 3,\n" +
            "                \"operateType\": 10,\n" +
            "                \"paramValue\": \"2\",\n" +
            "                \"successKeyword\": \"\",\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 4,\n" +
            "                \"serialNum\": 4,\n" +
            "                \"operateType\": 3,\n" +
            "                \"paramValue\": \"流量管家\",\n" +
            "                \"successKeyword\": \"\",\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 7,\n" +
            "                \"serialNum\": 5,\n" +
            "                \"operateType\": 10,\n" +
            "                \"paramValue\": \"3\",\n" +
            "                \"successKeyword\": null,\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 8,\n" +
            "                \"serialNum\": 6,\n" +
            "                \"operateType\": 4,\n" +
            "                \"paramValue\": \"resourceId=com.greenpoint.android.mc10086.activity:id/user_phoneno_edt\",\n" +
            "                \"successKeyword\": \"#phoneno\",\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 9,\n" +
            "                \"serialNum\": 7,\n" +
            "                \"operateType\": 3,\n" +
            "                \"paramValue\": \"获取验证码\",\n" +
            "                \"successKeyword\": null,\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 10,\n" +
            "                \"serialNum\": 8,\n" +
            "                \"operateType\": 8,\n" +
            "                \"paramValue\": \"resourceId=com.greenpoint.android.mc10086.activity:id/user_login_smspassword_edt\",\n" +
            "                \"successKeyword\": \"1\",\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 11,\n" +
            "                \"serialNum\": 9,\n" +
            "                \"operateType\": 3,\n" +
            "                \"paramValue\": \"登录\",\n" +
            "                \"successKeyword\": \"流量总量>>剩余 \",\n" +
            "                \"isTimestamp\": 1,\n" +
            "                \"scriptId\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 12,\n" +
            "                \"serialNum\": 10,\n" +
            "                \"operateType\": 9,\n" +
            "                \"paramValue\": \"com.greenpoint.android.mc10086.activity\",\n" +
            "                \"successKeyword\": null,\n" +
            "                \"isTimestamp\": 0,\n" +
            "                \"scriptId\": 1\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

    private void getData() throws IOException {
//        TaskInfo taskInfo = new Gson().fromJson(str, TaskInfo.class);
//        //保存数据到数据库
//        saveData(taskInfo);
//        Message message = handler.obtainMessage();
//        message.obj = taskInfo;
//        message.what = ObtainTaskService.START_WORK_APP;
//        handler.sendMessage(message);
        String imei = SystemUtils.getImei();
        StringRequest stringRequest = (StringRequest) new StringRequest(HttpContstant.URL + HttpContstant.GETTASK, RequestMethod.POST).add("imei", "67T7N16413001568");
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
                    //保存数据到数据库
                    saveData(taskInfo);
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

    private void saveData(TaskInfo taskInfo) {
        TaskInfo.DataBean data = taskInfo.getData();
        Script script = new Script();
        script.setScriptId(data.getScriptId());
        script.setTaskId(data.getTaskId());
        script.setStrategyId(data.getStrategyId());
        script.setOperator(data.getOperator());
        script.setProvince(data.getProvinceName());
        script.setBusinessName(data.getBusinessName());
        script.setAided(data.getIsAided());
        script.setInstanceName(data.getInstanceName());
        script.setPhoneNum(data.getPhoneNum());
        script.setTaskSerial(data.getTaskSerial());
        ScriptDao scriptDao = MyApplication.getDaoInstant().getScriptDao();
        long insert = scriptDao.insert(script);
        ScriptDetailsDao scriptDetailsDao = MyApplication.getDaoInstant().getScriptDetailsDao();
        for (TaskInfo.DataBean.ScriptInfosBean scriptInfosBean : data.getScriptInfos()) {
            ScriptDetails details = new ScriptDetails();
            details.setSerialNum(scriptInfosBean.getSerialNum());
            details.setOperateType(scriptInfosBean.getOperateType());
            details.setParamValue(scriptInfosBean.getParamValue());
            details.setSuccessKeyword(scriptInfosBean.getSuccessKeyword());
            details.setIsTimeStamp(scriptInfosBean.getIsTimestamp());
            details.setId(insert);
            scriptDetailsDao.save(details);
        }

    }
}

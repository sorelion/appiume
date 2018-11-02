package com.cmit.clouddetection.threadpool;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.cmit.clouddetection.activity.MyApplication;
import com.cmit.clouddetection.bean.TaskInfo;
import com.cmit.clouddetection.bean.TaskResultDetailInfo;
import com.cmit.clouddetection.bean.UploadTaskResultInfo;
import com.cmit.clouddetection.contstant.HttpContstant;
import com.cmit.clouddetection.dao.TaskResultDetailDao;
import com.cmit.clouddetection.entry.TaskResultDetail;
import com.cmit.clouddetection.utils.LogUtil;
import com.cmit.clouddetection.utils.TimerUtils;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pact on 2018/8/16.
 */

public class UploadTaskRunnbale implements Runnable {

    private int what;
    private Handler mHandler;
    private Context context;

    public UploadTaskRunnbale(Handler mHandler, int what, Context context) {
        this.context = context;
        this.mHandler = mHandler;
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
        mHandler.sendEmptyMessageDelayed(what, 1000 * 60 * 5);
    }


    /**
     * 上传日志 taskType 1 sms 2 -4g 3-app 4-webapp
     */
    public void uploadLog(TaskInfo taskInfo, TimerUtils timerUtils) {
        TaskInfo.DataBean data = taskInfo.getData();
        UploadTaskResultInfo uploadTaskResultInfo = new UploadTaskResultInfo();
        uploadTaskResultInfo.setInstanceName(data.getInstanceName());
        uploadTaskResultInfo.setTaskSerial(data.getTaskSerial());
        uploadTaskResultInfo.setTaskId(data.getTaskId());
        uploadTaskResultInfo.setScriptId(data.getScriptId());
        uploadTaskResultInfo.setOperator(data.getOperator());
        uploadTaskResultInfo.setProvinceName(data.getProvinceName());
        uploadTaskResultInfo.setBusinessName(data.getBusinessName());
        uploadTaskResultInfo.setChannel(data.getChannel());
        uploadTaskResultInfo.setStratrgyId(data.getStrategyId());
        uploadTaskResultInfo.setBeginTime(timerUtils.getStartTime());
        uploadTaskResultInfo.setEndTime(timerUtils.getEndTime());
        uploadTaskResultInfo.setTimeUse(Integer.parseInt(String.valueOf(timerUtils.getEndTime() - timerUtils.getStartTime())));
        uploadTaskResultInfo.setTaskContent(1);
        uploadTaskResultInfo.setResultDetail("测试");
        uploadTaskResultInfo.setPhoneNum(data.getPhoneNum());
        // 查询脚本明细
        List<TaskResultDetailInfo> taskResultDetailInfos = new ArrayList<>();
        TaskResultDetailDao taskResultDetailDao = MyApplication.getDaoInstant().getTaskResultDetailDao();
        List<TaskResultDetail> list = taskResultDetailDao.queryBuilder().where(TaskResultDetailDao.Properties.ScriptId.eq(data.getScriptId())).list();
        for (TaskResultDetail taskResultDetail : list) {
            TaskResultDetailInfo taskResultDetailInfo = new TaskResultDetailInfo();
            taskResultDetailInfo.setStepId(taskResultDetail.getId());
            taskResultDetailInfo.setSerialNum((float) taskResultDetail.getSerialNum());
            taskResultDetailInfo.setOperateType(taskResultDetail.getOperateNum());
            taskResultDetailInfo.setParamValue(taskResultDetail.getParamValue());
            taskResultDetailInfo.setSuccessKeyword(taskResultDetail.getSuccessKeyword());
            taskResultDetailInfo.setIsTimestamp(taskResultDetail.getIsTimeStamp());
//                taskResultDetailInfo.setRunningResult(taskResultDetail.getRunningResult());
            taskResultDetailInfo.setRunningResult(1);
            taskResultDetailInfo.setSingleStepBeginTime(taskResultDetail.getSingleStepBeginTime());
            taskResultDetailInfo.setSingleStepEndTime(taskResultDetail.getSingleStepEndTime());
            taskResultDetailInfo.setResultScreenshot(taskResultDetail.getResultScreenShot());
            taskResultDetailInfos.add(taskResultDetailInfo);
        }
        uploadTaskResultInfo.setList(taskResultDetailInfos);

        StringRequest stringRequest = new StringRequest(HttpContstant.URL + HttpContstant.UPLOADRESULT, RequestMethod.POST);
        String result = new Gson().toJson(uploadTaskResultInfo);
        stringRequest.setDefineRequestBodyForJson(result);
        NoHttp.newRequestQueue().add(2, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }
            @Override
            public void onSucceed(int what, Response<String> response) {

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

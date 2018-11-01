package com.cmit.clouddetection.threadpool;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.cmit.clouddetection.bean.TaskInfo;
import com.cmit.clouddetection.bean.UploadTaskResultInfo;
import com.cmit.clouddetection.contstant.HttpContstant;
import com.cmit.clouddetection.utils.LogUtil;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;

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
    public void uploadLog(TaskInfo taskInfo) {
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
//
//        // 查询脚本明细
//        try {
//            TaskResultDetailDao taskResultDetailDao = MyApplication.getDaoInstant().getTaskResultDetailDao();
//            List<TaskResultDetail> list = taskResultDetailDao.queryBuilder().where(TaskResultDetailDao.Properties.ScriptId.eq(data.getScriptId())).list();
//            for (TaskResultDetail taskResultDetail : list) {
//                switch (taskResultDetail.getOperateNum()) {
//                    case "2":
//                        taskResultDetail.setOperateNum("元素点击");
//                        break;
//                    case "3":
//                        taskResultDetail.setOperateNum("元素输入");
//                        break;
//                    case "4":
//                        taskResultDetail.setOperateNum("坐标点击");
//                        break;
//                    case "5":
//                        taskResultDetail.setOperateNum("点击回退键");
//                        break;
//                    case "11":
//                        taskResultDetail.setOperateNum("存在则点击");
//                        break;
//                    case "14":
//                        taskResultDetail.setOperateNum("截图");
//                        break;
//                    case "21":
//                        taskResultDetail.setOperateNum("等待");
//                        break;
//                    case "30":
//                        taskResultDetail.setOperateNum("滑动");
//                        break;
//                    case "43":
//                        taskResultDetail.setOperateNum("启动APP");
//                        break;
//                    case "44":
//                        taskResultDetail.setOperateNum("图片验证码");
//                        break;
//                    case "45":
//                        taskResultDetail.setOperateNum("短信验证码");
//                        break;
//                    case "46":
//                        taskResultDetail.setOperateNum("图像定位");
//                        break;
//                    case "47":
//                        taskResultDetail.setOperateNum("图片比对");
//                        break;
//                    case "48":
//                        taskResultDetail.setOperateNum("坐标位置输入");
//                        break;
//                    case "49":
//                        taskResultDetail.setOperateNum("停止APP");
//                        break;
//                    case "50":
//                        taskResultDetail.setOperateNum("清缓存");
//                        break;
//                }
//            }

//        StringRequest stringRequest = (StringRequest) new StringRequest(HttpContstant.URL + HttpContstant.UPLOADRESULT, RequestMethod.POST);
//        String result = new Gson().toJson(uploadTaskResultInfo);
//        stringRequest.setDefineRequestBodyForJson(result);
//        NoHttp.newRequestQueue().add(0, stringRequest, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                JSONObject result = JSONObject.parseObject(response.get());
//                int code = (Integer) result.get("code");
//                if (code == 0) {//有数据
//                    TaskInfo taskInfo = new Gson().fromJson(response.get(), TaskInfo.class);
//
//                    //没有任务
//                } else {
//                    LogUtil.getInstance().increaseLog((String) result.get("msg"), null);
//                }
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


//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}

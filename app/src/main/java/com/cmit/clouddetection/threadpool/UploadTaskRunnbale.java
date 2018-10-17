package com.cmit.clouddetection.threadpool;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cmit.clouddetection.activity.MyApplication;
import com.cmit.clouddetection.utils.LogUtil;
import com.cmit.clouddetection.utils.SystemUtils;

import freemarker.template.utility.StringUtil;


/**
 * Created by pact on 2018/8/16.
 */

public class UploadTaskRunnbale implements Runnable {

    private int what;
    private Handler mHandler;
     private Context context;
//    private SimpleDateFormat sdf_key = new SimpleDateFormat("yyyyMMddHHmmss");
//    private QueryBamNormalModelRespContentDao mQueryBamNormalModelRespContentDao;

    public UploadTaskRunnbale(Handler mHandler, int what, Context context) {
        this.context = context;
        this.mHandler = mHandler;
        this.what = what;

    }

    @Override
    public void run() {
//        if (FixThreadPools.iswork) {
//            try {
//                synchronized (FixThreadPools.getThreadLock()) {
//                    FixThreadPools.getThreadLock().wait();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        monitorAppTask();
//        mHandler.sendEmptyMessageDelayed(what, 1000 * 60 * 5);
    }


    /**
     * 每隔一段时间查看一次，任务是否已经完成
     * <p>
     * 监控App任务完成情况
     */
//    public void monitorAppTask() {
//        QueryBamNormalModelRespContentDao qbnmrcDaoApp = MyApplication.getDaoInstant().getQueryBamNormalModelRespContentDao();
//        List<QueryBamNormalModelRespContent> qbnmrcAppList = qbnmrcDaoApp.queryBuilder().where(QueryBamNormalModelRespContentDao.Properties.Statue.notEq(5), QueryBamNormalModelRespContentDao.Properties.Channel.eq("32")).list();
//        if (qbnmrcAppList != null && qbnmrcAppList.size() > 0) { //有任务
//            for (QueryBamNormalModelRespContent queryBamNormalModelRespContent : qbnmrcAppList) {
//                if (queryBamNormalModelRespContent.getBamNormalModel() == null) {
//                    BamNormalModelParaDao bamNormalModelParaDao = MyApplication.getDaoInstant().getBamNormalModelParaDao();
//                    BamNormalModelDetailParaDao bamNormalModelDetailParaDao = MyApplication.getDaoInstant().getBamNormalModelDetailParaDao();
//                    BamNormalModelPara bamNormalModelPara = bamNormalModelParaDao.queryBuilder().where(BamNormalModelParaDao.Properties.QbmId.eq(queryBamNormalModelRespContent.getId())).unique();
//                    List<BamNormalModelDetailPara> list = bamNormalModelDetailParaDao.queryBuilder().where(BamNormalModelDetailParaDao.Properties.QbmId.eq(queryBamNormalModelRespContent.getId())).list();
//                    bamNormalModelPara.setBamNormalModelDetailParas(list);
//                    queryBamNormalModelRespContent.setBamNormalModel(bamNormalModelPara);
//                    LogUtil.getInstance().increaseLog("查询出来的流水号=" + queryBamNormalModelRespContent.getTestsrl(), null);
//                }
//                saveLog(queryBamNormalModelRespContent);
//                //            saveLog(qbnmrcApp, 3); //3:APP探测任务
//            }
//
//        }
//    }

    /**
     * 保存任务执行日志 1 sms 2 -4g 3-app 4-webapp
     *
     * @param qbmr
     */
//    public void saveLog(QueryBamNormalModelRespContent qbmr) {
//
//        int taskTimeOut = Constants.taskAppTimeOut;
//        if (qbmr != null) { // 说明有任务
//            // 查看结果是否做完
//            TaskResultLogDao taskResultLogDao = MyApplication.getDaoInstant().getTaskResultLogDao();
//            List<TaskResultLog> list = taskResultLogDao.queryBuilder().where(TaskResultLogDao.Properties.QbmId.eq(qbmr.getId())).list();
//            if (list != null && list.size() > 0) {
//                TaskResultLog taskResultLog = list.get(0);
//                // 查看是否超时
//                long epoch = System.currentTimeMillis();
//                Long currentTime = epoch - qbmr.getCreateTime().getTime();
//                QueryBamNormalModelRespContentDao qbnmrcd = MyApplication.getDaoInstant().getQueryBamNormalModelRespContentDao();
//                // 任务做完了
//                if (currentTime > taskTimeOut) { // 超时
//                    // 提交请求...设置超时
//                    qbmr.setStatue(2);// 设置超时
//                    qbnmrcd.save(qbmr);
//                }
//                uploadLog(1, qbmr, taskResultLog);
//
//            }
//        }
//    }

    /**
     * 上传日志 taskType 1 sms 2 -4g 3-app 4-webapp
     */
//    private void uploadLog(int statue, QueryBamNormalModelRespContent qbmr, TaskResultLog taskResultLog) {
//        Taskresult taskresult = new Taskresult();
//        List<TestresultDetailPhone> TestresultDetailPhoneList = new ArrayList<TestresultDetailPhone>();
//        String timertoken = SharedPreferencesUtils.getConfigStr(context, Constants.cachName, "timertoken");
//        String BeginTimer = SharedPreferencesUtils.getConfigStr(context, Constants.cachName, "BeginTimer");
//        String EndTimer = SharedPreferencesUtils.getConfigStr(context, Constants.cachName, "EndTimer");
//        Long Wastetime = SharedPreferencesUtils.getConfigLong(context, Constants.cachName, "Wastetime");
//        switch (statue) {
//            //任务做完
//            case 1:
//                String result = taskResultLog.getResult();
//                String resultContent = taskResultLog.getResultContent();
//                if ("成功".equals(result)) {
//                    taskresult.setResult("0");
//                    StringBuffer sb = new StringBuffer();
//                    taskresult.setResultcontent("task_no_error");
//                } else {
//                    taskresult.setResult("1");
//                    taskresult.setResultcontent(resultContent);
//                    taskresult.setResultDetail(taskResultLog.getResultDetail());
//                    try {
//                        if (null != taskResultLog && null != taskResultLog.getTaskResultJson()) {
//                            Taskresult tmp = JSON.parseObject(taskResultLog.getTaskResultJson(), Taskresult.class);
//                            taskresult.setPiclink(tmp.getPiclink());
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                taskresult.setStrategyid(qbmr.getStrategyId());
//                taskresult.setGroupid(qbmr.getGroupId());
//                taskresult.setTaskid(qbmr.getObjectId());
//                taskresult.setTasksetid(qbmr.getTasksetId());
//                taskresult.setTasksetinstancename(qbmr.getTasksetInstanceName());
//                taskresult.setTimertoken(StringUtil.convertStrToNull(timertoken));
//                taskresult.setTimerstarttime(StringUtil.convertStrToNull(BeginTimer));
//                taskresult.setTimerendtime(StringUtil.convertStrToNull(EndTimer));
//                taskresult.setWastetime(Wastetime);
//                taskresult.setChannel(qbmr.getChannel());
//                taskresult.setTestsrl(qbmr.getTestsrl());
//                taskresult.setStrategyRunType(2);
//                break;
//        }
//
//        // 查询脚本明细
//        try {
//            BamNormalModelDetailParaDao bamNormalModelDetailParaDao = MyApplication.getDaoInstant().getBamNormalModelDetailParaDao();
//            List<BamNormalModelDetailPara> list = bamNormalModelDetailParaDao.queryBuilder().where(BamNormalModelDetailParaDao.Properties.QbmId.eq(qbmr.getId())).list();
//
//            StringBuffer sb = new StringBuffer();
//            if (list != null) {
//                for (BamNormalModelDetailPara bamNormalModelDetailPara : list) {
//                    if (StringUtil.isNotEmpty(bamNormalModelDetailPara.getReturncontent())) {
//                        sb.append("\r\n" + bamNormalModelDetailPara.getReturncontent());
//                    }
//                    if (StringUtil.isNotEmpty(bamNormalModelDetailPara.getErrMsg())) {
//                        taskresult.setResultDetail(bamNormalModelDetailPara.getErrMsg());
//                    }
//                    TestresultDetailPhone testresultDetailPhone = new TestresultDetailPhone();
//                    testresultDetailPhone.setTestsrl(qbmr.getTestsrl());
//                    testresultDetailPhone.setOrderid(bamNormalModelDetailPara.getStepNo());
//
//                    testresultDetailPhone.setSmsverify(StringUtil.convertStr2Long(bamNormalModelDetailPara.getSmsVerify()));
//                    testresultDetailPhone.setVerify(StringUtil.convertStr2Long(bamNormalModelDetailPara.getVerify()));
//                    testresultDetailPhone.setVerifyimg(bamNormalModelDetailPara.getVerifyId());
//                    testresultDetailPhone.setTarget(bamNormalModelDetailPara.getTarget());
//                    testresultDetailPhone.setSuccstr1(bamNormalModelDetailPara.getSuccstr1());
//                    testresultDetailPhone.setSuccstr2(bamNormalModelDetailPara.getSuccstr2());
//                    testresultDetailPhone.setSuccstr3(bamNormalModelDetailPara.getSuccstr3());
//                    testresultDetailPhone.setPostparam(bamNormalModelDetailPara.getPostParam());
//                    testresultDetailPhone.setErrmsg(bamNormalModelDetailPara.getErrMsg());
//                    testresultDetailPhone.setErrstr1(bamNormalModelDetailPara.getErrstr1());
//                    testresultDetailPhone.setErrstr2(bamNormalModelDetailPara.getErrstr2());
//                    testresultDetailPhone.setFailretry(0L);
//                    testresultDetailPhone.setRetrycount(0L);
//                    testresultDetailPhone.setTokentype(0);
//                    testresultDetailPhone.setTimertoken(bamNormalModelDetailPara.getTimerToken());
//                    testresultDetailPhone.setTimertoken(bamNormalModelDetailPara.getTimerToken());
//                    if (bamNormalModelDetailPara.getStatue() == 0) {
//                        testresultDetailPhone.setResult(0L);
//                    } else {
//                        testresultDetailPhone.setResult(1L);
//                    }
//                    testresultDetailPhone.setReturncontent(bamNormalModelDetailPara.getReturncontent());
//                    testresultDetailPhone.setDescribe(bamNormalModelDetailPara.getDescribe());
//                    testresultDetailPhone.setBegintime(bamNormalModelDetailPara.getBegintime());
//                    testresultDetailPhone.setEndtime(bamNormalModelDetailPara.getEndtime());
//                    testresultDetailPhone.setWastetime(bamNormalModelDetailPara.getWastetime());
//                    testresultDetailPhone.setTestorder(bamNormalModelDetailPara.getStepNo());
//                    testresultDetailPhone.setShotpic(bamNormalModelDetailPara.getShotpic());
//                    switch (bamNormalModelDetailPara.getOperateType()) {
//                        case "2":
//                            testresultDetailPhone.setMethod("元素点击");
//                            break;
//                        case "3":
//                            testresultDetailPhone.setMethod("元素输入");
//                            break;
//                        case "4":
//                            testresultDetailPhone.setMethod("坐标点击");
//                            break;
//                        case "5":
//                            testresultDetailPhone.setMethod("点击回退键");
//                            break;
//                        case "11":
//                            testresultDetailPhone.setMethod("存在则点击");
//                            break;
//                        case "14":
//                            testresultDetailPhone.setMethod("截图");
//                            break;
//                        case "21":
//                            testresultDetailPhone.setMethod("等待");
//                            break;
//                        case "30":
//                            testresultDetailPhone.setMethod("滑动");
//                            break;
//                        case "43":
//                            testresultDetailPhone.setMethod("启动APP");
//                            break;
//                        case "44":
//                            testresultDetailPhone.setMethod("图片验证码");
//                            break;
//                        case "45":
//                            testresultDetailPhone.setMethod("短信验证码");
//                            break;
//                        case "46":
//                            testresultDetailPhone.setMethod("图像定位");
//                            break;
//                        case "47":
//                            testresultDetailPhone.setMethod("图片比对");
//                            break;
//                        case "48":
//                            testresultDetailPhone.setMethod("坐标位置输入");
//                            break;
//                        case "49":
//                            testresultDetailPhone.setMethod("停止APP");
//                            break;
//                        case "50":
//                            testresultDetailPhone.setMethod("清缓存");
//                            break;
//                    }
//                    TestresultDetailPhoneList.add(testresultDetailPhone);
//                }
//            }
//
//            taskresult.setResultcontent(sb.toString());
//            taskresult.setTestsrl(qbmr.getTestsrl());
//            taskresult.setChannel("32");
//            uploadDataToServer(taskresult, TestresultDetailPhoneList, qbmr);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * 上传数据
     * 进行测试结果到服务器上面去
     */
//    private void uploadDataToServer(Taskresult taskresult, List<TestresultDetailPhone> TestresultDetailPhoneList, final QueryBamNormalModelRespContent qbmr) {
//        try {
//            taskresult.setInitiator(SystemUtils.getNetworkType(context));
//            taskresult.setInquirer(SystemUtils.getLocationName());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        MyXutilsRequestParams params = new MyXutilsRequestParams(Constants.IP + "/taskresult/upResultLog");
//        params.setCharset("utf-8");
//        TaskResultContent taskResultContent = new TaskResultContent();
//        taskResultContent.setImsi(SystemUtils.getIMEI(context));
//        taskResultContent.setPhoneNo(qbmr.getPhoneNo());
//        taskResultContent.setTaskresult(taskresult);
//        taskResultContent.setTestresultDetailPhones(TestresultDetailPhoneList);
//
//        qbmr.setStatue(3);
//
//        mQueryBamNormalModelRespContentDao = MyApplication.getDaoInstant().getQueryBamNormalModelRespContentDao();
//        mQueryBamNormalModelRespContentDao.save(qbmr);
//        RequestData requestData = new RequestData();
//        requestData.setData(taskResultContent);
//        requestData.setRequestTime(System.currentTimeMillis() + "");
//        String json = JSON.toJSONString(requestData);
//        Log.i("sore", json);
//        params.addBodyParameter("data", json);
//        SystemUtils.addKey2Req(params, this.sdf_key, context);
//        LogUtil.getInstance().increaseLog("上传任务结果 流水号=" + qbmr.getTestsrl() + " 时间=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(System.currentTimeMillis()))), null);
//
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.i("sore", "onSuccess");
//                Log.i("sore", result);
//                ResponeData responeData = JSON.parseObject(result, ResponeData.class);
//                LogUtil.getInstance().increaseLog("上传任务结果返回码  code=" + responeData.getCode(), null);
//
//                if (responeData != null && responeData.getCode() == 0) {
//                    qbmr.setStatue(5);
//                    qbmr.setLocalErroMsg(responeData.getMsg());
//
//                } else {
//                    qbmr.setStatue(4);
//                    qbmr.setLocalErroMsg(JSON.toJSONString(responeData));
//                }
//                try {
//                    mQueryBamNormalModelRespContentDao.save(qbmr);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.i("sore", "onError");
//                // Logger.v(ex.getMessage());
//                qbmr.setStatue(0);
//                qbmr.setLocalErroMsg("网络访问错误" + ex.getMessage());
//                mQueryBamNormalModelRespContentDao.save(qbmr);
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                Log.i("sore", "onCancelled");
//                // Logger.v("cancelled");
//            }
//
//            @Override
//            public void onFinished() {
//                Log.i("sore", "onFinished");
//            }
//        });
//    }
}

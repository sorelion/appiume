package com.cmit.clouddetection.threadpool;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cmit.clouddetection.activity.MyApplication;
import com.cmit.clouddetection.bean.TaskDetailInfo;
import com.cmit.clouddetection.bean.TaskInfo;
import com.cmit.clouddetection.dao.TaskResultDao;
import com.cmit.clouddetection.dao.TaskResultDetailDao;
import com.cmit.clouddetection.entry.TaskResult;
import com.cmit.clouddetection.entry.TaskResultDetail;
import com.cmit.clouddetection.exception.TaskException;
import com.cmit.clouddetection.socket.Parameters;
import com.cmit.clouddetection.socket.SocketConnect;
import com.cmit.clouddetection.socket.SocketRequest;
import com.cmit.clouddetection.utils.AdbUtils;
import com.cmit.clouddetection.utils.LogUtil;
import com.cmit.clouddetection.utils.ShotScreenUtils;
import com.cmit.clouddetection.utils.TimerUtils;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by pact on 2018/10/12.
 */

public class AppWorkRunnable implements Runnable {
    private TaskInfo taskInfo;
    private boolean isAutoLogin;
    private Context context;
    private char c = (char) -1; //结束符

    public AppWorkRunnable(Context context, boolean isAutoLogin, TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
        this.context = context;
        this.isAutoLogin = isAutoLogin;
    }

    @Override
    public void run() {
        ThreadPools.iswork = true;
        startAppWork(context);
        ThreadPools.iswork = false;
        ThreadPools.excute(new Runnable() {
            @Override
            public void run() {
                UploadTaskRunnbale uploadTaskRunnbale = new UploadTaskRunnbale(null, 0, context);
//                uploadTaskRunnbale.saveLog(qbmr);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (ThreadPools.getThreadLock()) {
                    ThreadPools.getThreadLock().notifyAll();
                }
            }
        });
    }

    /**
     * APP探测任务
     */
    private void startAppWork(final Context context) {
        TimerUtils totalTime = new TimerUtils();//计时器
        try {
            List<TaskDetailInfo> list = taskInfo.getList();
            LogUtil.getInstance().increaseLog("开始执行", null);
            totalTime.startTime();
            totalTime.setStartTime(System.currentTimeMillis());
            for (int i = 0; i < list.size(); i++) { // 解析脚本
                TaskDetailInfo listBean = list.get(i);
                //脚本解析
                resolveAppScript(listBean);
            }
            totalTime.stopTime();
            SocketConnect.close(); //断开socket连接
            saveResult("成功", null, totalTime);
        } catch (Exception e) {
            if (e instanceof TaskException) {
                totalTime.stopTime();
                saveResult("失败", e.getMessage(), totalTime);
                LogUtil.getInstance().increaseLog("错误一：" + e.getMessage(), null);
            } else {
                totalTime.stopTime();
                LogUtil.getInstance().increaseLog("错误二：" + e.getMessage(), null);
            }
        }
    }

    /**
     * 保存结果到本地
     */
    private void saveResult(String result, String resultContent, TimerUtils totalTime) {
        TaskResult taskResult = new TaskResult();
        taskResult.setTaskResult(result);
        TaskResultDao taskResultDao = MyApplication.getDaoInstant().getTaskResultDao();
        taskResultDao.save(taskResult);
    }

    private final String TYPE = "type";
    private final String VALUE = "value";
    private final String RESOURCEID = "resourceid=";
    private final String TEXT = "text=";
    private final String XPATH = "xpath=";

    /**
     * 从脚本中提取操作对象
     */
    public Map<String, String> extratTarget(String paramValue) {
        HashMap<String, String> hashMaps = new HashMap<>();
        if (paramValue.contains(RESOURCEID)) {
            hashMaps.put(TYPE, RESOURCEID);
            hashMaps.put(VALUE, paramValue.replace(RESOURCEID, ""));
        } else if (paramValue.contains(TEXT)) {
            hashMaps.put(TYPE, TEXT);
            hashMaps.put(VALUE, paramValue.replace(TEXT, ""));
        } else if (paramValue.contains(XPATH)) {
            hashMaps.put(TYPE, XPATH);
            hashMaps.put(VALUE, paramValue.replace(XPATH, ""));
            hashMaps.put("#sms#", paramValue.replace("#sms#", ""));
        } else if (paramValue.contains(",")) {
            String[] targetXY = paramValue.split(",");
            String x = targetXY[0];
            String y = targetXY[1];
            hashMaps.put(TYPE, "point");
            hashMaps.put("x", x);
            hashMaps.put("y", y);
        } else {
            hashMaps.put(TYPE, "text");
            hashMaps.put(VALUE, paramValue);
        }
        return hashMaps;
    }


    /**
     * 脚本解析
     */
    public void resolveAppScript(TaskDetailInfo listBean) throws Exception {
        int operateType = listBean.getOperateType();
        String paramValue = listBean.getParamValue();
        int scriptId = listBean.getScriptId();
        TimerUtils timerUtils = new TimerUtils(); // 计时器
        Map<String, String> valueMap = extratTarget(paramValue);
        switch (operateType) {
            case 0: // 点击
                String type = valueMap.get(TYPE);
                switch (type) {
                    //resoureceid
                    case RESOURCEID:
                        try {
                            Parameters params = new Parameters("id", "" + valueMap.get(VALUE), "", "", false);
                            SocketRequest socketRequest = new SocketRequest("action", "find", params);
                            String requestJson = new Gson().toJson(socketRequest);
                            requestJson = requestJson + String.valueOf(c);
                            String scriptResult = SocketConnect.send(requestJson);
                            LogUtil.getInstance().increaseLog(scriptResult, null);
                            if (scriptResult.contains("status")) {
                                JSONObject jsonObject = (JSONObject) JSON.parse(scriptResult);
                                int status = (int) jsonObject.get("status");
                                if (status == 0) {
                                    JSONObject element = (JSONObject) jsonObject.get("value");
                                    String elementid = (String) element.get("ELEMENT");
                                    params = new Parameters(elementid);
                                    socketRequest = new SocketRequest("action", "element:click", params);
                                    requestJson = JSON.toJSONString(socketRequest);
                                    requestJson = requestJson + String.valueOf(c);
                                    timerUtils.startTime();
                                    scriptResult = SocketConnect.send(requestJson);
                                }
                            }
                            checkSocketRespone("" + scriptId, scriptResult, timerUtils);
                        } catch (TaskException e) {
                            throw new TaskException("元素点击错误");
                        }
                        break;
                    //text(name)
                    case TEXT:
                        break;
                    //xpath
                    case XPATH:
                        break;
                    //坐标
                    case "point":
                        Parameters parameters = new Parameters(valueMap.get("x"), valueMap.get("y"));
                        SocketRequest socketRequest = new SocketRequest("action", "click", parameters);
                        String requestJson = JSON.toJSONString(socketRequest);
                        requestJson = requestJson + String.valueOf(c);
                        timerUtils.startTime();
                        String scriptResult = SocketConnect.send(requestJson);
                        checkSocketRespone("" + scriptId, scriptResult, timerUtils);
                        break;
                    //配置文字
                    case "text":
                        break;
                }
                break;
            case 1: // 元素输入
                Log.i("sore", "元素输入");
                Parameters params = new Parameters("id", "" + valueMap.get(VALUE), "", "", false);
                SocketRequest socketRequest = new SocketRequest("action", "find", params);
                String requestJson = JSON.toJSONString(socketRequest);
                requestJson = requestJson + String.valueOf(c);
                String scriptResult = SocketConnect.send(requestJson);
                LogUtil.getInstance().increaseLog(scriptResult, null);
                if (scriptResult.contains("status")) {
                    JSONObject jsonObject = (JSONObject) JSON.parse(scriptResult);
                    int status = (int) jsonObject.get("status");
                    if (status == 0) {
                        JSONObject value = (JSONObject) jsonObject.get("value");
                        String elementid = (String) value.get("ELEMENT");
                        String text = valueMap.get(VALUE); //取参数
                        params = new Parameters(elementid, text, false, true);
                        socketRequest = new SocketRequest("action", "element:setText", params);
                        requestJson = JSON.toJSONString(socketRequest);
                        requestJson = requestJson + String.valueOf(c);
                        timerUtils.startTime();
                        scriptResult = SocketConnect.send(requestJson);
                    }
                }
                checkSocketRespone("" + scriptId, scriptResult, timerUtils);
                break;
            case 2: // 点击回退键
                Log.i("sore", "点击回退键");
                String cmd = "input keyevent 4";
                timerUtils.startTime();
                int cmdResult = AdbUtils.execShellCmdSilent(cmd);
                timerUtils.stopTime();
                if (cmdResult == 0) {
                    LogUtil.getInstance().increaseLog("点击回退键成功", null);
                    checkSocketRespone("" + scriptId, "点击回推建成功", timerUtils);
                } else {
                    checkSocketRespone("" + scriptId, "点击回推建失败", timerUtils);
                    throw new TaskException("点击回退键失败");
                }
                break;
//            case "11":  // 存在则点击
//                Log.i("sore", "存在则点击");
//                target = bnmdp.getTarget().toLowerCase();
//                params = extratTarget(target);
//                socketRequest = new SocketRequest("action", "find", params);
//                requestJson = JSON.toJSONString(socketRequest);
//                requestJson = requestJson + String.valueOf(c);
//                scriptResult = SocketConnect.send(requestJson);
//                LogUtil.getInstance().increaseLog(scriptResult, null);
//                timerUtils.startTime();
//                if (scriptResult.contains("status")) {
//                    jsonObject = (JSONObject) JSON.parse(scriptResult);
//                    status = (int) jsonObject.get("status");
//                    if (status == 0) {
//                        element = (JSONObject) jsonObject.get("value");
//                        String elementid = (String) element.get("ELEMENT");
//                        params = new Parameters(elementid);
//                        socketRequest = new SocketRequest("action", "element:click", params);
//                        requestJson = JSON.toJSONString(socketRequest);
//                        requestJson = requestJson + String.valueOf(c);
//                        scriptResult = SocketConnect.send(requestJson);
//                    }
//                }
//                timerUtils.stopTime();
//                bnmdp.setBegintime(timerUtils.getStartTimeStr());
//                bnmdp.setQbmId(qbnmrc.getId());
//                bnmdp.setEndtime(timerUtils.getEndTimeStr());
//                bnmdp.setWastetime(timerUtils.getWasteTime());
//                bnmdp.setStatue(0);
//                bnmdp.setCreateTime(new Date());
//                bamNormalModelDetailParaDao = MyApplication.getDaoInstant().getBamNormalModelDetailParaDao();
//                bamNormalModelDetailParaDao.save(bnmdp);
//                break;
            case 3: //截图
                Log.i("sore", "截图");
                String picPath = Environment.getExternalStorageDirectory().getPath() + "/CloudDetectionVI/screen.png";
                timerUtils.startTime();// 打开计时器
                //cmdResult = AdbUtils.execShellCmdSilent("screencap -p " + pic); //执行截图指令
                try {
                    ShotScreenUtils shotScreenUtils = new ShotScreenUtils(context);
                    Bitmap bitmap = shotScreenUtils.startCapture(500); //截图操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 4: //等待
                Log.i("sore", "等待");
                String value = paramValue.toLowerCase();
                timerUtils.startTime();
                if (value.contains("fix=")) {
                    value = value.replace("fix=", "");
                    Thread.sleep(Long.valueOf(value) * 1000);
                    LogUtil.getInstance().increaseLog("等待" + value + "秒", null);
                    timerUtils.stopTime();
                    checkSocketRespone("" + scriptId, "等待", timerUtils);
                } else {
                    LogUtil.getInstance().increaseLog("脚本异常", null);
                    timerUtils.stopTime();
                    checkSocketRespone("" + scriptId, "脚本异常", timerUtils);
                    throw new TaskException("脚本异常");
                }
                break;
            case 5: // 滑动
                Log.i("sore", "滑动");
                LogUtil.getInstance().increaseLog("滑动", null);
                timerUtils.startTime();
                if (paramValue != null && paramValue.length() != 0) {
                    String[] coordinate = paramValue.split(",");
                    int startX = Integer.parseInt(coordinate[0]);
                    int startY = Integer.parseInt(coordinate[1]);
                    int endX = Integer.parseInt(coordinate[2]);
                    int endY = Integer.parseInt(coordinate[3]);
                    params = new Parameters(startX, startY, endX, endY, 14);
                    socketRequest = new SocketRequest("action", "swipe", params);
                    Log.i("sore", socketRequest.toString());
                    requestJson = JSON.toJSONString(socketRequest);
                    LogUtil.getInstance().increaseLog(requestJson, null);
                    requestJson = requestJson + String.valueOf(c);
                    scriptResult = SocketConnect.send(requestJson);
                    LogUtil.getInstance().increaseLog(scriptResult, null);
                    checkSocketRespone("" + scriptId, "脚本异常", timerUtils);
                } else {
                    LogUtil.getInstance().increaseLog("滑动", null);
                    timerUtils.stopTime();
                    checkSocketRespone("" + scriptId, "脚本异常", timerUtils);
                    throw new TaskException("脚本异常");
                }

                break;
            case 6:  // 启动APP
                Log.i("sore", "启动APP");

                if (!paramValue.isEmpty()) {
                    cmd = "am start -W -n " + paramValue + " -S";
                    timerUtils.startTime();
                    String[] split = paramValue.split("/");
                    //判断包名是否正确
                    boolean avilible = AdbUtils.isAvilible(context, split[0]);
                    if (avilible) {
                        cmdResult = AdbUtils.execShellCmdSilent(cmd);
                        timerUtils.stopTime();
                        if (cmdResult == 0) {
                            LogUtil.getInstance().increaseLog("APP启动成功", null);
                            timerUtils.stopTime();
                            checkSocketRespone("" + scriptId, "APP启动成功", timerUtils);
                        } else {
                            LogUtil.getInstance().increaseLog("APP启动失败", null);
                            timerUtils.stopTime();
                            checkSocketRespone("" + scriptId, "APP启动失败", timerUtils);
                            throw new TaskException("APP启动失败");
                        }
                    } else {
                        LogUtil.getInstance().increaseLog("APP不存在", null);
                        timerUtils.stopTime();
                        checkSocketRespone("" + scriptId, "APP不存在", timerUtils);
                        throw new TaskException("APP找不到，启动失败");
                    }

                } else {
                    LogUtil.getInstance().increaseLog("脚本异常", null);
                    timerUtils.stopTime();
                    checkSocketRespone("" + scriptId, "脚本异常", timerUtils);
                    throw new TaskException("脚本异常");
                }
                break;
//            case 7: //短信验证码
//                Log.i("sore", "短信验证码");
//                timerUtils.startTime();
//                Long currentappTime = System.currentTimeMillis(); //获取当前时间戳
//                if (!paramValue.isEmpty()) { // 操作值不为空
//                    if (!paramValue.equalsIgnoreCase("#sms#")) {// 如果有配置需要点击的元素或者坐标，先点击
//                        if (paramValue.contains("#sms#")) {
//                            paramValue = paramValue.replace("#sms#", "");
//                        }
//                        params = extratTarget(paramValue);
//                        socketRequest = new SocketRequest("action", "find", params);
//                        requestJson = JSON.toJSONString(socketRequest);
//                        requestJson = requestJson + String.valueOf(c);
//                        scriptResult = SocketConnect.send(requestJson);
//                        Logger.d(scriptResult);
//
//                        if (scriptResult.contains("status")) {
//                            jsonObject = (JSONObject) JSON.parse(scriptResult);
//                            status = (int) jsonObject.get("status");
//
//                            if (status == 0) {
//                                element = (JSONObject) jsonObject.get("value");
//                                String elementid = (String) element.get("ELEMENT");
//                                params = new Parameters(elementid);
//                                socketRequest = new SocketRequest("action", "element:click", params);
//                                requestJson = JSON.toJSONString(socketRequest);
//                                requestJson = requestJson + String.valueOf(c);
//                                timerUtils.startTime();
//
//                                scriptResult = SocketConnect.send(requestJson); //点击获取短信验证码按钮
//                                timerUtils.stopTime();
//                                Logger.d(scriptResult);
//
//                                if (scriptResult.contains("status")) {
//                                    jsonObject = (JSONObject) JSON.parse(scriptResult);
//                                    status = (int) jsonObject.get("status");
//                                    if (status == 0) {
//                                        // 有配置#sms#，表示需要从服务器获取短信验证码，否则从本机取短信验证码
//                                        if (bnmdp.getPostParam().toLowerCase().contains("#sms#")) {
//                                            getSmsVerifyFromServer(context, bnmp, qbnmrc, bnmdp, timerUtils, currentappTime);
//                                        } else {
//                                            getSmsVerifyFromLocal(context, bnmp, qbnmrc, bnmdp, list, timerUtils, currentappTime);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    getSmsVerifyFromLocal(context, bnmp, qbnmrc, bnmdp, list, timerUtils, currentappTime);
//                }
//
//                String smsverify = SharedPreferencesUtils.getConfigStr(context, Constants.cachName, "smsverify");
//                bnmdp.setSmsVerify(smsverify);
//                target = bnmdp.getTarget().toLowerCase();
//                params = extratTarget(target); //取出操作对象
//                socketRequest = new SocketRequest("action", "find", params);
//                requestJson = JSON.toJSONString(socketRequest);
//                requestJson = requestJson + String.valueOf(c);
//                scriptResult = SocketConnect.send(requestJson); //输入短信验证码
//                Logger.d(scriptResult);
//                if (scriptResult.contains("status")) {
//                    jsonObject = (JSONObject) JSON.parse(scriptResult);
//                    status = (int) jsonObject.get("status");
//                    if (status == 0) {
//                        element = (JSONObject) jsonObject.get("value");
//                        String elementid = (String) element.get("ELEMENT");
//                        params = new Parameters(elementid, smsverify, false, true);
//                        socketRequest = new SocketRequest("action", "element:setText", params); //输入验证码
//                        requestJson = JSON.toJSONString(socketRequest);
//                        requestJson = requestJson + String.valueOf(c);
//                        timerUtils.startTime();
//                        scriptResult = SocketConnect.send(requestJson);
//                        timerUtils.stopTime();
//                        Logger.d(scriptResult);
//                    }
//                }
//                checkSocketRespone(context, bnmp, bnmdp, qbnmrc, scriptResult, timerUtils, num, total);
//                break;
//            case "46": //图像定位
//                Log.i("sore", "图像定位");
//                timerUtils.startTime();
//                picUrl = bnmdp.getTarget();
//                if (StringUtil.isNotEmpty(picUrl)) {
//                    String fileName = picUrl.substring(picUrl.lastIndexOf('/') + 1);
//                    fileName = Environment.getExternalStorageDirectory() + "/CloudDetectionVI/" + fileName;
//                    FixThreadPools.excute(new DownloadFileRunnable(picUrl, new File(Environment.getExternalStorageDirectory() + "/CloudDetectionVI/")));
//                    Logger.i(fileName);
//                    FileHelper.deleteFile(Environment.getExternalStorageDirectory().getPath() + "/CloudDetectionVI/screen.png");
//                    timerUtils.startTime();// 打开计时器
//                    //cmdResult = AdbUtils.execShellCmdSilent("screencap -p " + screen); //执行截图指令
//                    ShotScreenUtils shotScreenUtils = new ShotScreenUtils(context);
//                    Bitmap srcBit = null;
//                    try {
//                        srcBit = shotScreenUtils.startCapture(500); //截图操作，获取目标截图
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    timerUtils.stopTime();// 停止计时器
//                    if (srcBit != null) {
//                        LogUtil.getInstance().increaseLog("截图成功", null);
//                        Bitmap templBit = null;
//                        try {
//                            Thread.sleep(1000);
//                            templBit = BitmapFactory.decodeStream(new FileInputStream(fileName)); //模板
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        MatchTemplateResult mtr = SystemUtils.getPixels(srcBit, templBit); //定位坐标
//                        params = new Parameters(String.valueOf(mtr.getResultLoc().x), String.valueOf(mtr.getResultLoc().y));
//                        socketRequest = new SocketRequest("action", "click", params); //坐标点击
//                        requestJson = JSON.toJSONString(socketRequest);
//                        requestJson = requestJson + String.valueOf(c);
//                        scriptResult = SocketConnect.send(requestJson);
//                    } else {
//                        LogUtil.getInstance().increaseLog("截图失败", null);
//                        timerUtils.stopTime();
//                        bnmdp.setBegintime(timerUtils.getStartTimeStr());
//                        bnmdp.setQbmId(qbnmrc.getId());
//                        bnmdp.setEndtime(timerUtils.getEndTimeStr());
//                        bnmdp.setWastetime(timerUtils.getWasteTime());
//                        bnmdp.setStatue(1);
//                        bnmdp.setErrMsg("截图失败");
//                        bnmdp.setCreateTime(new Date());
//                        bamNormalModelDetailParaDao = MyApplication.getDaoInstant().getBamNormalModelDetailParaDao();
//                        bamNormalModelDetailParaDao.save(bnmdp);
//                        throw new TaskException("截图失败");
//                    }
//                }
//                checkSocketRespone(context, bnmp, bnmdp, qbnmrc, scriptResult, timerUtils, num, total);
//                break;
//            case "47": //图片比对
//                Log.i("sore", "图片比对");
//                timerUtils.startTime();// 打开计时器
//                checkToken(context, bnmp, bnmdp, qbnmrc, timerUtils, num, total);
//                break;
//            case "48": //坐标位置输入（不常用，待完善）
//                break;
//            case "49": //停止APP
//                Log.i("sore", "停止APP");
//                target = bnmdp.getTarget();
//                if (target != null) {
//                    String cmd = "am force-stop " + target;
//                    timerUtils.startTime();
//                    int result = AdbUtils.execShellCmdSilent(cmd);
//                    timerUtils.stopTime();
//                    if (result == 0) {
//                        LogUtil.getInstance().increaseLog("APP关闭成功", null);
//                        bnmdp.setBegintime(timerUtils.getStartTimeStr());
//                        bnmdp.setQbmId(qbnmrc.getId());
//                        bnmdp.setEndtime(timerUtils.getEndTimeStr());
//                        bnmdp.setWastetime(timerUtils.getWasteTime());
//                        bnmdp.setStatue(0);
//                        bnmdp.setCreateTime(new Date());
//                        bamNormalModelDetailParaDao = MyApplication.getDaoInstant().getBamNormalModelDetailParaDao();
//                        bamNormalModelDetailParaDao.save(bnmdp);
//                    } else {
//                        LogUtil.getInstance().increaseLog("APP关闭失败", null);
//                        bnmdp.setBegintime(timerUtils.getStartTimeStr());
//                        bnmdp.setQbmId(qbnmrc.getId());
//                        bnmdp.setEndtime(timerUtils.getEndTimeStr());
//                        bnmdp.setWastetime(timerUtils.getWasteTime());
//                        bnmdp.setStatue(1);
//                        bnmdp.setErrMsg("APP关闭失败");
//                        bnmdp.setCreateTime(new Date());
//                        bamNormalModelDetailParaDao = MyApplication.getDaoInstant().getBamNormalModelDetailParaDao();
//                        bamNormalModelDetailParaDao.save(bnmdp);
//                        throw new TaskException("APP关闭失败");
//                    }
//                } else {
//                    LogUtil.getInstance().increaseLog("脚本异常", null);
//                    bnmdp.setBegintime(timerUtils.getStartTimeStr());
//                    bnmdp.setQbmId(qbnmrc.getId());
//                    bnmdp.setEndtime(timerUtils.getEndTimeStr());
//                    bnmdp.setWastetime(timerUtils.getWasteTime());
//                    bnmdp.setStatue(1);
//                    bnmdp.setErrMsg("脚本异常");
//                    bnmdp.setCreateTime(new Date());
//                    bamNormalModelDetailParaDao = MyApplication.getDaoInstant().getBamNormalModelDetailParaDao();
//                    bamNormalModelDetailParaDao.save(bnmdp);
//                    throw new TaskException("脚本异常");
//                }
//                break;
            case 8: //清缓存
                Log.i("sore", "清缓存");
                if (!paramValue.isEmpty()) {
                    cmd = "pm clear " + paramValue;
                    timerUtils.startTime();
                    int result = AdbUtils.execShellCmdSilent(cmd);
                    timerUtils.stopTime();
                    if (result == 0) {
                        LogUtil.getInstance().increaseLog("清缓存成功", null);
                        checkSocketRespone("" + scriptId, "清缓存成功", timerUtils);
                    } else {
                        LogUtil.getInstance().increaseLog("清缓存失败", null);
                        checkSocketRespone("" + scriptId, "清缓存失败", timerUtils);
                        throw new TaskException("清缓存失败");
                    }
                } else {
                    LogUtil.getInstance().increaseLog("脚本异常", null);
                    checkSocketRespone("" + scriptId, "脚本异常", timerUtils);
                    throw new TaskException("脚本异常");
                }
                break;
            default:
                LogUtil.getInstance().increaseLog("脚本异常", null);
                checkSocketRespone("" + scriptId, "脚本异常", timerUtils);
                throw new TaskException("脚本异常");
                //break;
        }
    }

    /**
     * scoket返回结果处理
     */
    private void checkSocketRespone(String scriptId, String result, TimerUtils timerUtils)
            throws Exception {
        TaskResultDetail taskResultDetail = new TaskResultDetail();
        if ("timeOut".equals(result)) {  //socket连接超时
            LogUtil.getInstance().increaseLog("socket连接超时：" + result, null);
            timerUtils.stopTime();
            taskResultDetail.setSingleStepBeginTime(timerUtils.getStartTimeStr());
            taskResultDetail.setScriptId(scriptId);
            taskResultDetail.setSingleStepEndTime(timerUtils.getEndTimeStr());
            taskResultDetail.setRunningResult("任务超时");
            //TODO 截图并上传

            //记录每次执行步骤结果
            TaskResultDetailDao taskResultDetailDao = MyApplication.getDaoInstant().getTaskResultDetailDao();
            taskResultDetailDao.save(taskResultDetail);
            throw new TaskException("socket连接超时");
        } else if ("SocketError".equals(result)) { //socket连接异常
            LogUtil.getInstance().increaseLog("socket连接异常：" + result, null);
            timerUtils.stopTime();
            taskResultDetail.setSingleStepBeginTime(timerUtils.getStartTimeStr());
            taskResultDetail.setScriptId(scriptId);
            taskResultDetail.setSingleStepEndTime(timerUtils.getEndTimeStr());
            taskResultDetail.setRunningResult("socket连接异常");
            //记录每次执行步骤结果
            TaskResultDetailDao taskResultDetailDao = MyApplication.getDaoInstant().getTaskResultDetailDao();
            taskResultDetailDao.save(taskResultDetail);
            throw new TaskException("socket连接异常");
        } else {  //socket连接正常
            JSONObject jsonObject = (JSONObject) JSON.parse(result);
            int status = (int) jsonObject.get("status"); //获取状态码
            if (status == 0) {
                LogUtil.getInstance().increaseLog(result, null);
                timerUtils.stopTime();
                taskResultDetail.setSingleStepBeginTime(timerUtils.getStartTimeStr());
                taskResultDetail.setScriptId(scriptId);
                taskResultDetail.setSingleStepEndTime(timerUtils.getEndTimeStr());
            } else {  // 状态不为0，任务失败
                timerUtils.stopTime();
                taskResultDetail.setSingleStepBeginTime(timerUtils.getStartTimeStr());
                taskResultDetail.setScriptId(scriptId);
                taskResultDetail.setSingleStepEndTime(timerUtils.getEndTimeStr());
                //TODO 截图并上传
                //记录每次执行步骤结果
                TaskResultDetailDao taskResultDetailDao = MyApplication.getDaoInstant().getTaskResultDetailDao();
                taskResultDetailDao.save(taskResultDetail);
                LogUtil.getInstance().increaseLog("任务执行失败，返回结果：" + result, null);
                throw new TaskException("任务执行失败，返回结果：" + result);
            }
        }
    }


}

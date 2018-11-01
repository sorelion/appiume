package com.cmit.clouddetection.threadpool;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmit.clouddetection.activity.MyApplication;
import com.cmit.clouddetection.bean.SMSInfo;
import com.cmit.clouddetection.bean.TaskInfo;
import com.cmit.clouddetection.contstant.HttpContstant;
import com.cmit.clouddetection.dao.TaskResultDao;
import com.cmit.clouddetection.dao.TaskResultDetailDao;
import com.cmit.clouddetection.entry.TaskResult;
import com.cmit.clouddetection.entry.TaskResultDetail;
import com.cmit.clouddetection.exception.TaskException;
import com.cmit.clouddetection.recognition.Constants;
import com.cmit.clouddetection.recognition.Demo_lyocr_general_ugc;
import com.cmit.clouddetection.recognition.pos;
import com.cmit.clouddetection.recognition.prism_wordsInfo;
import com.cmit.clouddetection.recognition.ugc_result;
import com.cmit.clouddetection.socket.Parameters;
import com.cmit.clouddetection.socket.SocketConnect;
import com.cmit.clouddetection.socket.SocketRequest;
import com.cmit.clouddetection.utils.AdbUtils;
import com.cmit.clouddetection.utils.LogUtil;
import com.cmit.clouddetection.utils.ShotScreenUtils;

import com.cmit.clouddetection.utils.TimerUtils;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.BitmapBinary;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;

import java.io.ByteArrayOutputStream;

import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.utility.StringUtil;


/**
 * Created by pact on 2018/10/12.
 */

public class AppWorkRunnable implements Runnable {
    private TaskInfo taskInfo;
    private Context context;
    private char c = (char) -1; //结束符
    private TaskResultDetailDao mTaskResultDetailDao;


    public AppWorkRunnable(Context context, TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
        this.context = context;
    }

    @Override
    public void run() {
        mTaskResultDetailDao = MyApplication.getDaoInstant().getTaskResultDetailDao();
        ThreadPools.iswork = true;
        startAppWork();
        UploadTaskRunnbale uploadTaskRunnbale = new UploadTaskRunnbale(null, 0, context);
        uploadTaskRunnbale.uploadLog(taskInfo);
        ThreadPools.iswork = false;
        synchronized (ThreadPools.getThreadLock()) {
            ThreadPools.getThreadLock().notifyAll();
        }
    }

    /**
     * APP探测任务
     */
    private void startAppWork() {
        TimerUtils totalTime = new TimerUtils();//计时器
        try {
            List<TaskInfo.DataBean.ScriptInfosBean> scriptInfos = taskInfo.getData().getScriptInfos();
            LogUtil.getInstance().increaseLog("开始执行", null);
            totalTime.startTime();
            totalTime.setStartTime(System.currentTimeMillis());
            List<TaskInfo.DataBean.SmsVerifycodeConfigsBean> smsVerifycodeConfigs = taskInfo.getData().getSmsVerifycodeConfigs();
            for (int i = 0; i < scriptInfos.size(); i++) { // 解析脚本
                TaskInfo.DataBean.ScriptInfosBean scriptInfosBean = scriptInfos.get(i);
                //脚本解析
                resolveAppScript(scriptInfosBean, smsVerifycodeConfigs.get(0));
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
    private final String RESOURCEID = "resourceId=";
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
    public void resolveAppScript(final TaskInfo.DataBean.ScriptInfosBean listBean, TaskInfo.DataBean.SmsVerifycodeConfigsBean smsverify) throws Exception {
        int operateType = listBean.getOperateType();
        final TimerUtils timerUtils = new TimerUtils(); // 计时器
        String paramValue = listBean.getParamValue();
        //(1.启动APP，2.清缓存，3.点击，4.输入，5.回退，6.截图，7.滑动，8.短信验证码，9.关闭APP,10.等待)
        switch (operateType) {
            case 1:  // 启动APP
//                Log.i("sore", "启动APP");
//                if (!paramValue.isEmpty()) {
//                    String cmd = "am start -W -n " + paramValue + " -S";
//                    timerUtils.startTime();
//                    String[] split = paramValue.split("/");
//                    //判断包名是否正确
//                    boolean avilible = AdbUtils.isAvilible(context, split[0]);
//                    if (avilible) {
//                        int cmdResult = AdbUtils.execShellCmdSilent(cmd);
//                        timerUtils.stopTime();
//                        saveDataForResult(listBean, timerUtils, cmdResult, null);
//                    } else {
//                        LogUtil.getInstance().increaseLog("APP不存在", null);
//                        timerUtils.stopTime();
//                        saveDataForResult(listBean, timerUtils, 1, null);
//                        throw new TaskException("APP找不到，启动失败");
//                    }
//                } else {
//                    LogUtil.getInstance().increaseLog("启动APP脚本异常", null);
//                    timerUtils.stopTime();
//                    saveDataForResult(listBean, timerUtils, 1, null);
//                    throw new TaskException("启动APP脚本异常");
//                }
                break;
            case 2: //清缓存
//                Log.i("sore", "清缓存");
//                if (!paramValue.isEmpty()) {
//                    String cmd = "pm clear " + paramValue;
//                    timerUtils.startTime();
//                    int result = AdbUtils.execShellCmdSilent(cmd);
//                    saveDataForResult(listBean, timerUtils, result, null);
//                    timerUtils.stopTime();
//                } else {
//                    LogUtil.getInstance().increaseLog("清缓存脚本异常", null);
//                    throw new TaskException("清缓存脚本异常");
//                }
                break;
            case 3: // 点击
                Map<String, String> valueMap = extratTarget(paramValue);
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
//                            checkSocketRespone("" + scriptId, scriptResult, timerUtils);
                        } catch (Exception e) {
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
//                        checkSocketRespone("" + scriptId, scriptResult, timerUtils);
                        break;
                    //配置文字
                    case "text":
//                        SocketConnect.initBoostrapSocket(); //每次任务初始化一次
//                        Socket socket = new Socket("localhost", 4724); //创建连接
//                        socket.isConnected();
//                        OutputStream os = socket.getOutputStream();
//                        ShotScreenUtils shotScreenUtils = new ShotScreenUtils(context);
//                        Bitmap bitmap = shotScreenUtils.startCapture(500); //截图操作
//                        String imgBase64 = imageToBase(bitmap);
//                        //UGCOCR 的上传报文格式：{//图像数据：base64编码，要求base64编码后大小不超过4M，最短边至少15px，最长边最大4096px，支持jpg/png/bmp格式，和url参数只能同时存在一个\"img\":\"\",//图像url地址：图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px，支持jpg/png/bmp格式，和img参数只能同时存在一个\"url\":\"\",//是否需要识别结果中每一行的置信度，默认不需要。true：需要false：不需要\"prob\":false}";
//                        String body = "{\"img\": \"" + imgBase64 + "\",\"configure\":{ \"min_size\" : 16, \"prob\" : false }}";
//                        Demo_lyocr_general_ugc ugc = new Demo_lyocr_general_ugc();
//                        ugc.ocrUgcHttpsTest(body);
//                        Thread.sleep(15000);
//                        ugc_result result = new Gson().fromJson(Constants.AliResult, ugc_result.class);
//                        Point point = getResultPointByUGC(result.getPrism_wordsInfo(), paramValue);
//                        String cmd = "{\"cmd\":\"action\",\"action\":\"click\",\"params\":{\"x\":" + point.x + ",\"y\":" + point.y + "}}";
//                        Thread.sleep(5000);
//                        String jsonString = cmd + String.valueOf(c);
//                        os.write((jsonString).getBytes("utf-8"));
//                        os.flush();
//                        os.close();
//                        socket.close();
                        break;
                }
                break;
            case 4: // 元素输入
                //com.greenpoint.android.mc10086.activity:id/user_phoneno_edt
                Log.i("sore", "元素输入");
                Map<String, String> stringStringMap = extratTarget(paramValue);
                String value = stringStringMap.get(VALUE);
                Parameters params = new Parameters("id", value, "", "", false);
                SocketRequest socketRequest = new SocketRequest("action", "find", params);
                String requestJson = JSON.toJSONString(socketRequest);
                requestJson = requestJson + String.valueOf(c);
                String scriptResult = SocketConnect.send(requestJson);
//                        LogUtil.getInstance().increaseLog(scriptResult, null);
//                        if (scriptResult.contains("status")) {
//                            JSONObject jsonObject = (JSONObject) JSON.parse(scriptResult);
//                            int status = (int) jsonObject.get("status");
//                            if (status == 0) {
//                        JSONObject value = (JSONObject) jsonObject.get("value");
//                        String elementid = (String) value.get("ELEMENT");
////                        String text = valueMap.get(VALUE); //取参数
//                        params = new Parameters(elementid, text, false, true);
//                        socketRequest = new SocketRequest("action", "element:setText", params);
//                        requestJson = JSON.toJSONString(socketRequest);
//                        requestJson = requestJson + String.valueOf(c);
//                        timerUtils.startTime();
//                        scriptResult = SocketConnect.send(requestJson);
//                    }
//                }
//                checkSocketRespone("" + scriptId, scriptResult, timerUtils);
                break;
            case 5: // 点击回退键
                Log.i("sore", "点击回退键");
                String cmd = "input keyevent 4";
                timerUtils.startTime();
                int cmdResult = AdbUtils.execShellCmdSilent(cmd);
                timerUtils.stopTime();
                saveDataForResult(listBean, timerUtils, cmdResult, null);
                break;
            case 6: //截图
                Log.i("sore", "截图");
                String time = LogUtil.timeStamp2Date(String.valueOf(System.currentTimeMillis()));
                String picPath = Environment.getExternalStorageDirectory().getPath() + "/CloudDetectionVI/screen.png";
                timerUtils.startTime();// 打开计时器
                ShotScreenUtils shotScreenUtils = new ShotScreenUtils(context);
                Bitmap bitmap = shotScreenUtils.startCapture(500); //截图操作
                timerUtils.stopTime();
                //保存到sd卡
//                shotScreenUtils.createPNG(bitmap, picPath);
                saveDataForResult(listBean, timerUtils, 0, null);
//                try {
//                    StringRequest stringRequest = (StringRequest) new StringRequest(HttpContstant.URL + HttpContstant.UPLOADIMAGE, RequestMethod.POST).add("file", new BitmapBinary(bitmap, "screen.png"));
//                    NoHttp.newRequestQueue().add(1, stringRequest, new OnResponseListener<String>() {
//                        @Override
//                        public void onStart(int what) {
//
//                        }
//
//                        @Override
//                        public void onSucceed(int what, Response<String> response) {
//                            JSONObject result = JSONObject.parseObject(response.get());
//                            int code = (Integer) result.get("code");
//                            try {
//                                if (code == 0) {
//                                    saveDataForResult(listBean, timerUtils, 0, ((String) result.get("data")));
//                                } else {
//                                    saveDataForResult(listBean, timerUtils, 1, null);
//                                }
//                            } catch (TaskException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFailed(int what, Response<String> response) {
//                            try {
//                                saveDataForResult(listBean, timerUtils, 1, null);
//                            } catch (TaskException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFinish(int what) {
//
//                        }
//                    });
//                } catch (Exception e) {
//                    throw new TaskException("截图上传失败");
//                }
                break;

            case 7: // 滑动
                Log.i("sore", "滑动");
                LogUtil.getInstance().

                        increaseLog("滑动", null);
                timerUtils.startTime();
                if (paramValue != null && paramValue.length() != 0)

                {
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
//                    checkSocketRespone("" + scriptId, "脚本异常", timerUtils);
                } else

                {
                    LogUtil.getInstance().increaseLog("滑动", null);
                    timerUtils.stopTime();
//                    checkSocketRespone("" + scriptId, "脚本异常", timerUtils);
                    throw new TaskException("脚本异常");
                }

                break;
            case 8: //短信验证码
//                String keyword = smsverify.getKeyword();
//                int length = smsverify.getLength();
//                Log.i("sore", "短信验证码");
//                Long currentTime = 0L;// 当前时间计时
//                boolean isTimeOut = false;//是否超时
//                String content = null;
//                while (!isTimeOut) {
//                    Thread.sleep(1000 * 10);
//                    currentTime += 1000 * 10;
//                    SMSInfo smsInfo = getFirstSmsInfo(context);
//                    if (smsInfo != null) {
//                        content = smsInfo.getContent();
//                        isTimeOut = true;
//                    }
//                    if (currentTime > 1000 * 60) {
//                        saveDataForResult(listBean, timerUtils, 1, null);
//                        throw new TaskException("验证码超时");
//                    }
//                }
//                String[] split = content.split(keyword);
//                content = split[1].substring(0, length + 1);
//                timerUtils.startTime();
//                //取出操作对象
//                paramValue = extratTarget(paramValue).get(VALUE);
//                params = new Parameters("id", paramValue, "", "", false);
//                socketRequest = new SocketRequest("action", "find", params);
//                requestJson = JSON.toJSONString(socketRequest);
//                requestJson = requestJson + String.valueOf(c);
//                scriptResult = SocketConnect.send(requestJson);
//                if (scriptResult.contains("status")) {
//                    JSONObject jsonObject = (JSONObject) JSON.parse(scriptResult);
//                    int status = (int) jsonObject.get("status");
//                    if (status == 0) {
//                        JSONObject value1 = (JSONObject) jsonObject.get("value");
//                        String elementid = (String) value1.get("ELEMENT");
//                        params = new Parameters(elementid, content, false, true);
//                        socketRequest = new SocketRequest("action", "element:setText", params);
//                        requestJson = JSON.toJSONString(socketRequest);
//                        requestJson = requestJson + String.valueOf(c);
//                        SocketConnect.send(requestJson);
//                        saveDataForResult(listBean, timerUtils, 0, null);
//                    } else {
//                        saveDataForResult(listBean, timerUtils, 1, null);
//                    }
//                }

                break;
            case 9: //停止APP
                Log.i("sore", "停止APP");

                if (paramValue != null) {
                    cmd = "am force-stop " + paramValue;
                    timerUtils.startTime();
                    int result = AdbUtils.execShellCmdSilent(cmd);
                    timerUtils.stopTime();
                    saveDataForResult(listBean, timerUtils, result, null);
                } else {
                    LogUtil.getInstance().increaseLog("脚本异常", null);
                    throw new TaskException("脚本异常");
                }
                break;
            case 10: //等待
                Log.i("sore", "等待");
                timerUtils.startTime();
                Thread.sleep(Long.valueOf(paramValue) * 1000);
                LogUtil.getInstance().

                        increaseLog("等待" + paramValue + "秒", null);
                timerUtils.stopTime();

                saveDataForResult(listBean, timerUtils, 0, null);
                break;
            default:
                LogUtil.getInstance().

                        increaseLog("脚本异常", null);
//                checkSocketRespone("" + scriptId, "脚本异常", timerUtils);
                throw new

                        TaskException("脚本异常");
        }
    }

    /**
     * 保存清缓存、启动APP等操作结果
     */

    private void saveDataForResult(TaskInfo.DataBean.ScriptInfosBean listBean, TimerUtils
            timerUtils, int result, String resultScreenShot) throws TaskException {
        TaskResultDetail taskResultDetail = new TaskResultDetail();
        taskResultDetail.setScriptId(listBean.getScriptId());
        taskResultDetail.setSerialNum(listBean.getSerialNum());
        taskResultDetail.setOperateNum(listBean.getOperateType());
        taskResultDetail.setParamValue(listBean.getParamValue());
        taskResultDetail.setSuccessKeyword(listBean.getSuccessKeyword());
        taskResultDetail.setIsTimeStamp(listBean.getIsTimestamp());
        taskResultDetail.setSingleStepBeginTime(timerUtils.getStartTimeStr());
        taskResultDetail.setSingleStepEndTime(timerUtils.getEndTimeStr());
        taskResultDetail.setResultScreenShot(resultScreenShot);
        if (result == 0) {
            taskResultDetail.setRunningResult("操作成功");
            mTaskResultDetailDao.save(taskResultDetail);
        } else {
            taskResultDetail.setRunningResult("操作失败");
            mTaskResultDetailDao.save(taskResultDetail);
            throw new TaskException("操作失败");
        }
    }


    public static Point getResultPointByUGC(List<prism_wordsInfo> prism_wordsInfos, String
            cmd) {
        Point point = new Point();
        for (prism_wordsInfo prismWordsInfo : prism_wordsInfos) {

            if (prismWordsInfo.getWord().equals(cmd)) {
                List<pos> posList = prismWordsInfo.getPos();
                point.x = (int) (posList.get(0).getX() + 0.5 * (posList.get(1).getX() - posList.get(0).getX()));
                point.y = (int) (posList.get(0).getY() + 0.5 * (posList.get(3).getY() - posList.get(0).getY()));
            }
        }
        return point;
    }

    public static String imageToBase(Bitmap bitmap) {
        //convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        //base64 encode
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String imgBase64 = new String(encode);
        return imgBase64;
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
//            taskResultDetail.setScriptId(scriptId);
            taskResultDetail.setSingleStepEndTime(timerUtils.getEndTimeStr());
            taskResultDetail.setRunningResult("任务超时");
            //TODO 截图并上传
            shotBitmapAndUpLoad();
            //记录每次执行步骤结果
            TaskResultDetailDao taskResultDetailDao = MyApplication.getDaoInstant().getTaskResultDetailDao();
            taskResultDetailDao.save(taskResultDetail);
            throw new TaskException("socket连接超时");
        } else if ("SocketError".equals(result)) { //socket连接异常
            LogUtil.getInstance().increaseLog("socket连接异常：" + result, null);
            timerUtils.stopTime();
            taskResultDetail.setSingleStepBeginTime(timerUtils.getStartTimeStr());
//            taskResultDetail.setScriptId(scriptId);
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
//                taskResultDetail.setScriptId(scriptId);
                taskResultDetail.setSingleStepEndTime(timerUtils.getEndTimeStr());
            } else {  // 状态不为0，任务失败
                timerUtils.stopTime();
                taskResultDetail.setSingleStepBeginTime(timerUtils.getStartTimeStr());
//                taskResultDetail.setScriptId(scriptId);
                taskResultDetail.setSingleStepEndTime(timerUtils.getEndTimeStr());
                //TODO 截图并上传
                shotBitmapAndUpLoad();
                //记录每次执行步骤结果
                TaskResultDetailDao taskResultDetailDao = MyApplication.getDaoInstant().getTaskResultDetailDao();
                taskResultDetailDao.save(taskResultDetail);
                LogUtil.getInstance().increaseLog("任务执行失败，返回结果：" + result, null);
                throw new TaskException("任务执行失败，返回结果：" + result);
            }
        }
    }

    private void shotBitmapAndUpLoad() throws InterruptedException {
        NoHttp.initialize(context);
        ShotScreenUtils shotScreenUtils = new ShotScreenUtils(context);
        Bitmap bitmap = shotScreenUtils.startCapture(500); //截图操作
        StringRequest stringRequest = (StringRequest) new StringRequest(HttpContstant.IP + HttpContstant.UPLOADIMAGE, RequestMethod.POST).add("file", JSON.toJSONString(bitmap));
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

    /**
     * Role:获取短信的各种信息 <BR>
     * Date:2012-3-19 <BR>
     *
     * @author CODYY)peijiangping
     */
    public SMSInfo getFirstSmsInfo(Context context) {
        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";

        ContentResolver cr = null;
        Cursor cur = null;
        try {
            cr = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            Uri uri = Uri.parse(SMS_URI_ALL);
            cur = cr.query(uri, projection, null, null, "date desc");
            if (null == cur) {
                return null;
            }
            if (cur.moveToFirst()) {
                String name;
                String phoneNumber;
                String smsbody;
                String date;
                String type;

                int nameColumn = cur.getColumnIndex("person");
                int phoneNumberColumn = cur.getColumnIndex("address");
                int smsbodyColumn = cur.getColumnIndex("body");
                int dateColumn = cur.getColumnIndex("date");
                int typeColumn = cur.getColumnIndex("type");

                do {
                    name = cur.getString(nameColumn);
                    phoneNumber = cur.getString(phoneNumberColumn);
                    smsbody = cur.getString(smsbodyColumn);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss.SSS");
                    Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                    date = dateFormat.format(d);

                    int typeId = cur.getInt(typeColumn);
                    if (typeId == 1) {
                        type = "接收";
                        SMSInfo smsInfo = new SMSInfo();
                        smsInfo.setCreateTime(d);
                        smsInfo.setContent(smsbody);
                        smsInfo.setMobile(phoneNumber);

                        return smsInfo;
                    } else if (typeId == 2) {
                        type = "发送";
                    } else {
                        type = "";
                    }

                    if (smsbody == null) smsbody = "";
                } while (cur.moveToNext());
            }

        } catch (Exception ex) {
            Logger.d(ex.getMessage());
        } finally {
            if (null != cur && !cur.isClosed()) {
                cur.close();
            }
        }
        return null;
    }
}



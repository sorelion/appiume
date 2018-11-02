package com.cmit.clouddetection.contstant;

/**
 * Created by pact on 2018/9/28.
 */

public interface HttpContstant {
    //    String IP = "http://192.168.119.215";
//    String IP = "http://192.168.191.1";
    String IP = "http://192.168.43.11";
    String PORT = "8083";
    String URL = IP + ":" + PORT;
    //请求任务
    String GETTASK = "/taskExecution/response";
    //上传图片
    String UPLOADIMAGE = "/taskExecution/upload";
    //任务结果上传
    String UPLOADRESULT = "/taskExecution/report";
    //任务手机信息
    String UPLOADMACHINE = "/machine/upload";
}

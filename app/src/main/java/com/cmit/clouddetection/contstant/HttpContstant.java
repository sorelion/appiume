package com.cmit.clouddetection.contstant;

/**
 * Created by pact on 2018/9/28.
 */

public interface HttpContstant {
    String IP = "http://192.168.191.1:8083";
    String PORT = "8083";
    String URL = IP + ":" + PORT;
    //请求任务
    String GETTASK = "/taskExecution/response";
    //上传图片
    String UPLOADIMAGE = "/taskExecution/upload";
}

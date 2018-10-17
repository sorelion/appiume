package com.cmit.clouddetection.contstant;

/**
 * Created by pact on 2018/10/11.
 */

public interface Constants {
    //服务的包名
    String PACKAGE_OBTAINTASK = "com.cmit.clouddetection.service.ObtainTaskService";
    String PACKAGE_PHONEINFO = "com.cmit.clouddetection.service.PhoneInfoService";
    String PACKAGE_UPLOADTASK = "com.cmit.clouddetection.service.TaskService";

    // 普通模式从服务器获取探测任务的间隔
    int GETTESTTASKRATE_NORMAL = 1000 * 60;
    // 高频率模式从服务器获取探测任务的间隔
    int GETTESTTASKRATE_HIGHRATE = 1000 * 20;
    // 演示模式从服务器获取探测任务的间隔
    int GETTESTTASKRATE_SHOW = 1000 * 5;

    // Appium 的 Bootstrap 监听的端口
    String SOCKET_HOST = "localhost";
    int SOCKER_PORT = 4724;
    int SOCKETTIMEOUT = 240000;
}

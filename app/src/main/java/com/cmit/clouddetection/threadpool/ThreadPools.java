package com.cmit.clouddetection.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pact on 2018/9/27.
 */

public class ThreadPools {
    public static boolean iswork = false;//标示脚本任务是否在执行
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
    //线程锁
    private static Object object = new Object();
    public static void excute(Runnable task) {
        fixedThreadPool.execute(task);
    }
    public static Object getThreadLock() {
        return object;
    }
}

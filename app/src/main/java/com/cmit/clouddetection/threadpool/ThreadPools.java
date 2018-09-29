package com.cmit.clouddetection.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pact on 2018/9/27.
 */

public class ThreadPools {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public static void excute(Runnable task) {
        fixedThreadPool.execute(task);
    }
}

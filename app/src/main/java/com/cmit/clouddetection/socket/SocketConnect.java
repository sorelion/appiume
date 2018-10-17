package com.cmit.clouddetection.socket;

import com.cmit.clouddetection.activity.MyApplication;
import com.cmit.clouddetection.contstant.Constants;
import com.cmit.clouddetection.exception.TaskException;
import com.cmit.clouddetection.utils.AdbUtils;
import com.cmit.clouddetection.utils.LogUtil;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by admin on 2018/7/3.
 */

public class SocketConnect {

    private static Socket mSocket;
    private static InputStream in = null;
    private static OutputStream out = null;

    public static Socket getSocket() {
        if (mSocket == null) {
            mSocket = new Socket();
        }
        if (!mSocket.isConnected()) {
            try {
                initBoostrapSocket(); //每次任务初始化一次
                mSocket = new Socket();
                mSocket.setSoTimeout(Constants.SOCKETTIMEOUT); //读取超时时间
                mSocket.connect(new InetSocketAddress(Constants.SOCKET_HOST, Constants.SOCKER_PORT));//设置连接请求超时时间30 s
            } catch (IOException e) {
                LogUtil.getInstance().increaseLog("socket初始化出问题了", null);
                return null;
            } catch (TaskException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return mSocket;
    }

    /*
   * 启动appium自动化测试的框架Socket服务
   * */
    private static void initBoostrapSocket() throws TaskException, InterruptedException {
        AdbUtils.stopProcess("uiautomator");// 停止之前的uiautomator进程
        // 启动app socket
        // AppDriverEntry该类必须去继承UiAutomatorTestCase---->自动去调用setUp(); 启动Socket服务
        boolean isStarted = AdbUtils.execShellCmd("uiautomator runtest  /data/data/com.cmit.clouddetection/app_appiumBootstrap" + File.separator + "AppiumBootstrap.jar -c io.appium.android.bootstrap.Bootstrap");
        if (isStarted) {
            LogUtil.getInstance().increaseLog("bootstrap启动成功", null);
        } else {
            LogUtil.getInstance().increaseLog("bootstrap启动失败", null);
            throw new TaskException("bootstrap启动失败");
        }
        Thread.sleep(2000);
    }

    private static String result;

    /*
    * 发送socket请求
    * */
    public static String send(final String socketRequest) {
        try {
            out = SocketConnect.getSocket().getOutputStream();
            out.write(socketRequest.getBytes("utf-8"));
            StringBuilder recvStrBuilder = new StringBuilder();
            in = SocketConnect.getSocket().getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            char[] buf = new char[1024];
            int readBytes = -1;
            while ((readBytes = bufferedReader.read(buf)) != -1) {
                String tempStr = new String(buf, 0, readBytes);
                recvStrBuilder.append(tempStr);
                result = recvStrBuilder.toString();
                if (tempStr.length() < 1024) {
                    break;
                }
            }
            //Logger.d("读完了, readBytes" + readBytes);
        } catch (SocketTimeoutException e) {

        } catch (IOException e) {

        } catch (Exception e) {

        } finally {
            return result;
        }

    }

    /*
    * 结束，关闭socket连接
    * */
    public static void close() {
        try {
            in.close();
            out.close();
            mSocket.close();
        } catch (SocketTimeoutException e) {

        } catch (IOException e) {

        } catch (Exception e) {

        }
    }

}


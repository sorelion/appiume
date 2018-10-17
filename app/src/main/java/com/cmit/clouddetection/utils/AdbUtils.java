package com.cmit.clouddetection.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by pact on 2018/10/11.
 */

public class AdbUtils {
    /**
     * 判断相对应的APP是否存在
     *
     * @param context
     * @param packageName(包名)
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        //获取手机系统的所有APP包名，然后进行一一比较
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (((PackageInfo) pinfo.get(i)).packageName
                    .equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /*
  * 查询真机processName进程并杀死
  * @param processName
  * */
    public static void stopProcess(String processName) {
        String pscmd = "ps | grep " + processName;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("su");
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(pscmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();

            InputStream inputstream = process.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            String line = "";
            StringBuilder sb = new StringBuilder(line);
            while ((line = bufferedreader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            process.waitFor();
            String[] l = sb.toString().split("root");

            for (String s : l) {
                if (s.contains(processName)) {
                    String[] ls = s.split(" ");
                    for (String p : ls) {
                        if (p.length() > 0) {
                            killPorcess(p);
                            break;
                        }
                    }
                }
            }
            bufferedreader.close();
            process.getOutputStream().close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * kill真机进程
     */
    public static void killPorcess(String processId) {
        String cmd = "kill -9 " + processId;
        execShellCmdSilent(cmd);
    }

    /*
 * 执行shell指令并且输出结果
 * @param cmd
 * */
    public static int execShellCmdSilent(String cmd) {
        Process process = null;
        OutputStream outputStream = null;
        DataOutputStream dataOutputStream = null;
        int result = -1;
        try {
            // 申请获取root权限
            process = Runtime.getRuntime().exec("su");
            // 获取输出流
            outputStream = process.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(cmd + "\n");
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            result = process.exitValue();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                    outputStream.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        return result;
    }

    /*
   * 执行shell指令并且输出结果
   * @param cmd
   * */
    public static boolean execShellCmd(String cmd) {
        Process process = null;
        OutputStream outputStream = null;
        DataOutputStream dataOutputStream = null;
        BufferedReader bufferedReader = null;
        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            process = Runtime.getRuntime().exec("su");
            // 获取输出流
            outputStream = process.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            String line = null;
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
            while ((line = bufferedReader.readLine()) != null) {

                if ("INSTRUMENTATION_STATUS_CODE: 1".equals(line)) {
                    return true;
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        } finally {
            if (dataOutputStream != null){
                try {
                    bufferedReader.close();
                    process.getOutputStream().close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        return false;
    }

}

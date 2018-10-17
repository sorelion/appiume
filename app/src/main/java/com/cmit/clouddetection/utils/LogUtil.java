package com.cmit.clouddetection.utils;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogUtil {
    private static LogUtil single = null;

    // 静态工厂方法
    public static LogUtil getInstance() {
        if (single == null) {
            single = new LogUtil();
        }
        return single;
    }

    public static String pathname = Environment.getExternalStorageDirectory() + File.separator + "/cloudDetectionLog";

    public void increaseLog(String data, Handler handler) {
        String filePath = pathname + "/" + timeStamp2DateByToday(System.currentTimeMillis() + "") + ".txt";
        try {
            if (!new File(pathname).exists()) {
                new File(pathname).mkdir();
            }
            File file = new File(filePath);
            FileOutputStream fos = null;
            if (!file.exists()) {
                File filelist = new File(pathname);
                File[] files = filelist.listFiles();
                for (File file1 : files) {
                    if (!file1.getName().equals(filePath)) {
                        file1.delete();
                    }
                }
                file.createNewFile();//如果文件不存在，就创建该文件
                fos = new FileOutputStream(file);//首次写入获取
            } else {
                //如果文件已存在，那么就在文件末尾追加写入
                fos = new FileOutputStream(file, true);//这里构造方法多了一个参数true,表示在文件末尾追加写入
            }
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//指定以UTF-8格式写入文件
            String str = timeStamp2Date(System.currentTimeMillis() + "") + "------" + data;
            osw.write(str);
            osw.write("\r\n");
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.what = 0;
                message.obj = str;
                handler.sendMessage(message);
            }
            //写入完成关闭流
            osw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String timeStamp2DateByToday(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String format1 = null;
        try {
            format1 = sdf.format(new Date(Long.valueOf(seconds)));
            return format1;
        } catch (Exception e) {
            return "NumberFormatException";
        }
    }

    public static String timeStamp2Date(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String format1 = null;
        try {
            format1 = sdf.format(new Date(Long.valueOf(seconds)));
            return format1;
        } catch (Exception e) {
            return "NumberFormatException";
        }
    }


}

package com.cmit.clouddetection.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.cmit.clouddetection.bean.NetworkState;

import java.lang.reflect.Method;

import static com.cmit.clouddetection.bean.NetworkState.NETWORK_MOBILE;
import static com.cmit.clouddetection.bean.NetworkState.NETWORK_NONE;
import static com.cmit.clouddetection.bean.NetworkState.NETWORK_WIFI;

/**
 * Created by pact on 2018/9/29.
 */

public class SystemUtils {
    //获取手机卡唯一标示
    public static  String getImsi(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return tm.getSubscriberId();
    }
    //获取手机唯一标示
    public static String getImei() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    //获取网络类型
    public  static NetworkState getNetworkState(Context mContext) {
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE); // 获取网络服务
        if (null == connManager) {//为空则认为无网络
            return NETWORK_NONE;
        }
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORK_NONE;
        }
        // 判断是否为WIFI
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_WIFI;
                }
            }
        }
        return NETWORK_MOBILE;
    }


}

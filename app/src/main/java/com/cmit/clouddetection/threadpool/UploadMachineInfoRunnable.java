package com.cmit.clouddetection.threadpool;

import android.Manifest;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.BatteryManager;

import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.cmit.clouddetection.R;
import com.cmit.clouddetection.bean.MachineInfo;
import com.cmit.clouddetection.bean.PhoneInfo;

import com.cmit.clouddetection.contstant.HttpContstant;
import com.cmit.clouddetection.utils.SystemUtils;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by pact on 2018/9/27.
 */

public class UploadMachineInfoRunnable implements Runnable {
    private Context mContext;

    public UploadMachineInfoRunnable(Context context) {
        mContext = context;
    }

    @Override
    public void run() {
        MachineInfo machineInfo = new MachineInfo();
        machineInfo.setImei(SystemUtils.getImei());
        machineInfo.setImsi(new SystemUtils().getImsi(mContext));
        machineInfo.setElectricity(getElectricity());
        machineInfo.setNetworkType(SystemUtils.getNetworkState(mContext).getState());
        machineInfo.setPhonePosition(getPhonePosition());
        machineInfo.setAppVersion(getVersionCode());
        machineInfo.setType(android.os.Build.BRAND + " " + android.os.Build.MODEL);
        machineInfo.setInstalledSoftInfo(new Gson().toJson(getInstalledApps()));
        StringRequest stringRequest = new StringRequest(HttpContstant.URL + HttpContstant.UPLOADMACHINE, RequestMethod.POST);
        String result = new Gson().toJson(machineInfo);
        stringRequest.setDefineRequestBodyForJson(result);

        NoHttp.newRequestQueue().add(3, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {

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

    //获取电量信息
    public int getElectricity() {
        BatteryManager batteryManager = (BatteryManager) mContext.getSystemService(mContext.BATTERY_SERVICE);
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }


    // 获取地址信息
    public String getPhonePosition() {
        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        String provider = judgeProvider(lm);
        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location lastKnownLocation = lm.getLastKnownLocation(provider);
            if (lastKnownLocation != null) {
                Geocoder gc = new Geocoder(mContext, Locale.getDefault());
                try {
                    List<Address> result = gc.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
                    return result.toString();
                } catch (IOException e) {
                    return mContext.getResources().getString(R.string.location_err);
                }
            }
        }
        return mContext.getResources().getString(R.string.location_err);
    }

    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        return prodiverlist.get(0);
    }

    //获取手机软件安装信息
    public ArrayList<PhoneInfo> getInstalledApps() {
        ArrayList<PhoneInfo> res = new ArrayList<PhoneInfo>();
        List<PackageInfo> packs = mContext.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo packageInfo = packs.get(i);
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                // 系统程序
                continue;
            } else {
                // 不是系统程序
                PhoneInfo phoneInfo = new PhoneInfo();
                phoneInfo.setAppName(appInfo.loadLabel(mContext.getPackageManager()).toString());
                phoneInfo.setPackageName(packageInfo.packageName);
                phoneInfo.setVersionName(packageInfo.versionName);
                phoneInfo.setVersionCode(packageInfo.versionCode);
                res.add(phoneInfo);
            }
        }
        return res;
    }

    //获取当前app版本
    public int getVersionCode() {
        int versionCode = 0;
        try {
            //获取软件版本号，对应android:versionCode
            versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}

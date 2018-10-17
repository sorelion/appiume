package com.cmit.clouddetection.fragment;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cmit.clouddetection.contstant.SPContsant;
import com.cmit.clouddetection.databinding.FragmentAppBinding;
import com.cmit.clouddetection.utils.SPUtils;
import com.cmit.clouddetection.utils.SystemUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
/**
 * Created by pact on 2018/9/26.
 */

public class AppFragment extends Fragment {

    private FragmentAppBinding mFragmentAppBinding;
    private TextView mInfo1;
    private TextView mInfo2;
    private TextView mInfo3;
    private TextView mInfo4;
    private TextView mInfo5;
    private MyPhoneStateListener myListener;
    private TelephonyManager tm;
    private EditText mEtResource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentAppBinding = FragmentAppBinding.inflate(inflater);
        initData();
        return mFragmentAppBinding.getRoot();
    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                /*隐藏软键盘*/
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                if ("".equals(mEtResource.getText().toString())) {
                    Toast.makeText(getActivity(), "资源编号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    SPUtils.putString(getActivity(), SPContsant.RESOURCE_ID, mEtResource.getText().toString());
                    Toast.makeText(getActivity(), "资源编号保存成功", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        }
    };

    private void initData() {
        mInfo1 = mFragmentAppBinding.tvMonitorInfo1;
        mInfo2 = mFragmentAppBinding.tvMonitorInfo2;
        mInfo3 = mFragmentAppBinding.tvMonitorInfo3;
        mInfo4 = mFragmentAppBinding.tvMonitorInfo4;
        mInfo5 = mFragmentAppBinding.tvMonitorInfo5;
        mEtResource = mFragmentAppBinding.etResource;
        if (!"".equals(SPUtils.getString(getActivity(), SPContsant.RESOURCE_ID, ""))) {
            mEtResource.setText(SPUtils.getString(getActivity(), SPContsant.RESOURCE_ID));
        }
        mEtResource.setOnKeyListener(onKeyListener);
        mInfo1.setText("手机名称：" + android.os.Build.BRAND + " "
                + android.os.Build.MODEL + "\n" + "手机串号："
                + SystemUtils.getImei() + "\n" + "手机卡串号："
                + new SystemUtils().getImsi(getActivity()) + "\n" + "操作系统："
                + "Android" + "\n" + "操作系统版本号："
                + android.os.Build.VERSION.RELEASE + "\n" + "手机分辨率："
                + getScreenResolution() + "\n" + "cpu型号："
                + getCpuName() + "\n" + "内存大小："
                + getTotalRam() + "M" + "\n" + "存储大小："
                + getTotalRom() + "M" + "\n" + "cpu使用率："
                + Math.round(getUsage()) + "%" + "\n" + "内存使用率："
                + getRamUse(getActivity()) + "%" + "\n" + "存储使用率："
                + getRomUse() + "%" + "\n");
        mInfo2.setText("当前网络类型：" + SystemUtils.getNetworkState(getActivity()).getState() + "\n");
        myListener = new MyPhoneStateListener();
        tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(myListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_SERVICE_STATE);
        getActivity().registerReceiver(batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        mInfo5.setText("已安装程序：\n" + getAllApp());
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            String imsi;
            try {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    imsi = null;
                } else {
                    imsi = tm.getSubscriberId();
                }
            } catch (Exception e) {
                imsi = null;
            }
            if (imsi != null) {
                if (null != imsi) {
                    int asu = signalStrength.getGsmSignalStrength();
                    mInfo3.setText("信号强度：" + asu + "asu");
                }
            }
        }
    }

    /**
     * 获取手机已安装的非系统应用程序信息
     *
     * @param
     * @return
     */
    public String getAllApp() {
        String result = "";
        List<PackageInfo> packages = getActivity().getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo pi : packages) {
            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                result += pi.applicationInfo.loadLabel(getActivity().getPackageManager()).toString()
                        + " " + pi.versionName + " " + pi.packageName + "\n";
            }
        }
        return result.substring(0, result.length() - 1);
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                int voltage = intent.getIntExtra("voltage", 0);
                int temp = intent.getIntExtra("temperature", 0);
                mInfo4.setText("剩余电量：" + level * 100 / scale + "%" + "\n"
                        + "电池电压：" + (long) voltage + "MV" + "\n"
                        + "电池温度：" + new BigDecimal(temp * 0.1).setScale(0, BigDecimal.ROUND_HALF_UP) + "℃");
                DecimalFormat df = new DecimalFormat("0");

            }
        }
    };

    //获取手机分辨率
    public String getScreenResolution() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels + "x" + outMetrics.heightPixels;
    }

    /**
     * 获取CPU型号
     *
     * @return
     */
    public String getCpuName() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] strs;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader br = new BufferedReader(fr);
            str2 = br.readLine();
            strs = str2.split("\\s+");
            for (int i = 2; i < strs.length; i++) {
                cpuInfo[0] = cpuInfo[0] + strs[i] + " ";
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuInfo[0];
    }

    /**
     * 获取总内存大小
     *
     * @return
     */
    public static long getTotalRam() {
        String str1 = "/proc/meminfo";
        String str2;
        String[] strs;
        long totalRam = 0L;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader br = new BufferedReader(fr, 8192);
            str2 = br.readLine();
            strs = str2.split("\\s+");
            totalRam = Integer.valueOf(strs[1]).intValue() / 1024;
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalRam;
    }

    /**
     * 获取存储大小
     *
     * @return
     */
    public long getTotalRom() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize / (1024 * 1024);
    }

    private double usage = 0;
    private long total = 0;
    private long idle = 0;

    /**
     * 获取CPU使用率
     *
     * @return
     */
    public double getUsage() {
        readUsage();
        return usage;
    }

    public void readUsage() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            String[] toks = load.split(" ");
            long currTotal = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4]);
            long currIdle = Long.parseLong(toks[5]);
            usage = (currTotal - total) * 100.0f / (currTotal - total + currIdle - idle);
            total = currTotal;
            idle = currIdle;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取内存使用率
     *
     * @return
     */
    public String getRamUse(Context mContext) {
        long total = getTotalRam();
        long avail = getAvailRam(mContext);
        long use = total - avail;
        String ramUse = String.valueOf(use * 100 / total);
        return ramUse;
    }

    /**
     * 获取可用内存大小
     *
     * @param mContext
     * @return
     */
    public static long getAvailRam(Context mContext) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / (1024 * 1024);
    }

    /**
     * 获取存储使用率
     *
     * @return
     */
    public static String getRomUse() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalRom = stat.getBlockCount() * blockSize / (1024 * 1024);
        long availRom = stat.getAvailableBlocks() * blockSize / (1024 * 1024);
        long usedRom = totalRom - availRom;
        String romUse = String.valueOf(usedRom * 100 / totalRom);
        return romUse;
    }
}

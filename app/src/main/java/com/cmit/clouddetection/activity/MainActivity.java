package com.cmit.clouddetection.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmit.clouddetection.R;
import com.cmit.clouddetection.adapter.ViewPagerAdapter;

import com.cmit.clouddetection.databinding.ActivityMainBinding;

import com.cmit.clouddetection.fragment.AboutFragment;
import com.cmit.clouddetection.fragment.AppFragment;
import com.cmit.clouddetection.fragment.DebugFragment;
import com.cmit.clouddetection.fragment.MainFragment;

import com.cmit.clouddetection.service.ObtainTaskService;
import com.cmit.clouddetection.service.UploadMachineInfoService;
import com.cmit.clouddetection.threadpool.ThreadPools;
import com.cmit.clouddetection.utils.AdbUtils;
import com.cmit.clouddetection.utils.LogUtil;
import com.cmit.clouddetection.utils.SystemUtils;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MAIN_FRAGMENT = 0;
    private static final int DEBUG_FRAGMENT = 1;
    private static final int APP_FRAGMENT = 2;
    private static final int ABOUT_FRAGMENT = 3;
    ActivityMainBinding mainBinding;
    private ViewPager main_view_pager;
    private ImageView mAboutIv;
    private ImageView mJcIv;
    private TextView mJcTv;
    private ImageView mMsgIv;
    private TextView mMsgTv;
    private TextView mAboutTv;
    private ImageView mPhoneIv;
    private TextView mPhoneTv;
    private LinearLayout mTabJc;
    private LinearLayout mTabMsg;
    private LinearLayout mTabPhoneBtn2;
    private LinearLayout mTabAboutBtn2;
    private List<Fragment> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用databing绑定界面
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
        MediaProjectionManager mMediaProjectionManager = (MediaProjectionManager) getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (MyApplication.getInstan().getIntent() == null && MyApplication.getInstan().getResult() == 0) {
            startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), 1);
        } else {
            registerService();
        }

    }

    //界面切换监听
    private ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            View view = MainActivity.this.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            //选择页面
            setCurrentItem(position);
        }
    };

    private void init() {
        initID();
        initFragment();
        ininListener();
        setCurrentItem(MAIN_FRAGMENT);
        //拷贝jar和安装openCV
        ThreadPools.excute(new Runnable() {
            @Override
            public void run() {
                copyApkFromAssets("AppiumBootstrap.jar");
                boolean avilible = AdbUtils.isAvilible(MainActivity.this, "org.opencv.engine");
                if (!avilible) {
                    try {
                        InputStream mInputStream = getAssets().open("OpenCV.apk");
                        String filePath = Environment.getExternalStorageDirectory() + "/opencv";
                        File file = new File(filePath);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        File mFile = new File(filePath + "/OpenCV.apk");
                        if (!mFile.exists()) {
                            mFile.createNewFile();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(mFile);
                        byte[] bytes = new byte[1024];
                        int i = 0;
                        while ((i = mInputStream.read(bytes)) > 0) {
                            fileOutputStream.write(bytes, 0, i);
                        }
                        mInputStream.close();
                        fileOutputStream.close();
                        SystemUtils.installSlient(filePath + "/OpenCV.apk");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /**
     * 将jar包拷贝到本地
     */
    public void copyApkFromAssets(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File distDir = this.getDir("appiumBootstrap", Activity.MODE_PRIVATE);
            File distFile = new File(distDir.getAbsolutePath() + File.separator + fileName);
            if (!distFile.exists())
                distFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(distFile);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.flush();// 刷新缓冲区
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void registerService() {
        //开启上传手机信息服务
        startService(new Intent(this, UploadMachineInfoService.class));
        //开启获取任务服务
//        startService(new Intent(this, ObtainTaskService.class));
    }

    private void ininListener() {
        main_view_pager.addOnPageChangeListener(pageChangeListener);
        mTabJc.setOnClickListener(this);
        mTabMsg.setOnClickListener(this);
        mTabPhoneBtn2.setOnClickListener(this);
        mTabAboutBtn2.setOnClickListener(this);
    }

    private void initFragment() {
        mList = new ArrayList<>();
        AboutFragment aboutFragment = new AboutFragment();
        AppFragment appFragment = new AppFragment();
        DebugFragment debugFragment = new DebugFragment();
        MainFragment mainFragment = new MainFragment();
        mList.add(mainFragment);
        mList.add(debugFragment);
        mList.add(appFragment);
        mList.add(aboutFragment);
        main_view_pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mList));
    }

    private void initID() {
        main_view_pager = mainBinding.mainViewPager;
        mJcIv = mainBinding.jcIv;
        mJcTv = mainBinding.jcTv;
        mMsgIv = mainBinding.msgIv;
        mMsgTv = mainBinding.msgTv;
        mAboutIv = mainBinding.aboutIv;
        mAboutTv = mainBinding.aboutTv;
        mPhoneIv = mainBinding.phoneIv;
        mPhoneTv = mainBinding.phoneTv;
        mTabJc = mainBinding.tabJc;
        mTabMsg = mainBinding.tabMsg;
        mTabPhoneBtn2 = mainBinding.tabPhoneBtn2;
        mTabAboutBtn2 = mainBinding.tabAboutBtn2;
    }

    public void setCurrentItem(int currentItem) {
        switch (currentItem) {
            case MAIN_FRAGMENT:
                mJcTv.setTextColor(this.getResources().getColor(R.color.font_blue));
                mMsgTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mPhoneTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mAboutTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mJcIv.setBackground(getDrawable(R.mipmap.jc_pre));
                mMsgIv.setBackground(getDrawable(R.mipmap.msg_nor));
                mPhoneIv.setBackground(getDrawable(R.mipmap.phone_nor));
                mAboutIv.setBackground(getDrawable(R.mipmap.gy_nor));
                break;
            case DEBUG_FRAGMENT:
                mJcTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mMsgTv.setTextColor(this.getResources().getColor(R.color.font_blue));
                mPhoneTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mAboutTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mJcIv.setBackground(getDrawable(R.mipmap.jc_nor));
                mMsgIv.setBackground(getDrawable(R.mipmap.msg_pre));
                mPhoneIv.setBackground(getDrawable(R.mipmap.phone_nor));
                mAboutIv.setBackground(getDrawable(R.mipmap.gy_nor));
                break;
            case APP_FRAGMENT:
                mJcTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mMsgTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mPhoneTv.setTextColor(this.getResources().getColor(R.color.font_blue));
                mAboutTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mJcIv.setBackground(getDrawable(R.mipmap.jc_nor));
                mMsgIv.setBackground(getDrawable(R.mipmap.msg_nor));
                mPhoneIv.setBackground(getDrawable(R.mipmap.phone_pre));
                mAboutIv.setBackground(getDrawable(R.mipmap.gy_nor));
                break;
            case ABOUT_FRAGMENT:
                mJcTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mMsgTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mPhoneTv.setTextColor(this.getResources().getColor(R.color.font_gray));
                mAboutTv.setTextColor(this.getResources().getColor(R.color.font_blue));
                mJcIv.setBackground(getDrawable(R.mipmap.jc_nor));
                mMsgIv.setBackground(getDrawable(R.mipmap.msg_nor));
                mPhoneIv.setBackground(getDrawable(R.mipmap.phone_nor));
                mAboutIv.setBackground(getDrawable(R.mipmap.gy_pre));
                break;
        }
        main_view_pager.setCurrentItem(currentItem);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_jc:
                setCurrentItem(MAIN_FRAGMENT);
                break;
            case R.id.tab_msg:
                setCurrentItem(DEBUG_FRAGMENT);
                break;
            case R.id.tab_phone_btn2:
                setCurrentItem(APP_FRAGMENT);
                break;
            case R.id.tab_about_btn2:
                setCurrentItem(ABOUT_FRAGMENT);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    //OpenCV库加载并初始化成功后的回调函数
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            // TODO Auto-generated method stub
            switch (status) {
                case BaseLoaderCallback.SUCCESS:
                    LogUtil.getInstance().increaseLog("OpenCV库成功加载", null);
                    Log.i("MainActivity", "OpenCV库成功加载");
                    break;
                default:
                    super.onManagerConnected(status);
                    LogUtil.getInstance().increaseLog("OpenCV库加载失败", null);
                    Log.i("MainActivity", "OpenCV库加载失败");
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            MyApplication.getInstan().setIntent(data);
            MyApplication.getInstan().setResult(resultCode);
            registerService();
        }
    }
}


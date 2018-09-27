package com.cmit.clouddetection.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用databing绑定界面
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    //界面切换监听
    private ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            //选择页面
            setCurrentItem(position);
        }
    };

    private void init() {
        initID();
        initFragment();
        ininListener();
        setCurrentItem(MAIN_FRAGMENT);
    }

    private void ininListener() {
        main_view_pager.addOnPageChangeListener(pageChangeListener);
        mTabJc.setOnClickListener(this);
        mTabMsg.setOnClickListener(this);
        mTabPhoneBtn2.setOnClickListener(this);
        mTabAboutBtn2.setOnClickListener(this);
    }

    private void initFragment() {
        List<Fragment> list = new ArrayList<>();
        AboutFragment aboutFragment = new AboutFragment();
        AppFragment appFragment = new AppFragment();
        DebugFragment debugFragment = new DebugFragment();
        MainFragment mainFragment = new MainFragment();
        list.add(mainFragment);
        list.add(debugFragment);
        list.add(appFragment);
        list.add(aboutFragment);
        main_view_pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), list));
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
}

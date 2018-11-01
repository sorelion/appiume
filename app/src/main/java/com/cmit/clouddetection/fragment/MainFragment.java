package com.cmit.clouddetection.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cmit.clouddetection.R;
import com.cmit.clouddetection.contstant.Constants;
import com.cmit.clouddetection.databinding.FragmentMainBinding;
import com.cmit.clouddetection.service.ObtainTaskService;
import com.cmit.clouddetection.service.UploadMachineInfoService;
import com.cmit.clouddetection.service.UploadPhoneInfoService;
import com.cmit.clouddetection.utils.SystemUtils;

/**
 * Created by pact on 2018/9/26.
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    private FragmentMainBinding mFragmentMainBinding;
    private Intent mIntent;
    private TextView mRebootService;
    private TextView mDataUploadStatue;
    private TextView mPhoneInfoStatue;
    private TextView mPhoneStatue;
    private TextView mTaskTitleStatue;
    private TextView mAppBtn;
    private TextView mSmsBtn;
    private RadioGroup mRadioGroup;
    private RadioButton checkRadioButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentMainBinding = FragmentMainBinding.inflate(inflater);
        initID();
        init();
        return mFragmentMainBinding.getRoot();
    }

    private void initID() {
        mRebootService = mFragmentMainBinding.rebootService;
        mTaskTitleStatue = mFragmentMainBinding.taskTitleStatue;
        mDataUploadStatue = mFragmentMainBinding.dataUploadStatue;
        mPhoneInfoStatue = mFragmentMainBinding.phoneInfoStatue;
        mAppBtn = mFragmentMainBinding.appBtn;
        mSmsBtn = mFragmentMainBinding.smsBtn;
        mRadioGroup = mFragmentMainBinding.radioGroup;
    }

    private void init() {
        mIntent = new Intent(getActivity(), ObtainTaskService.class);
        //拿到中间人对象，当任务频率改变时，改变服务中的频率
        mRebootService.setOnClickListener(this);
        getActivity().bindService(mIntent, taskFrequencyConn, Context.BIND_AUTO_CREATE);
        //任务模式选项
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkRadioButton = mRadioGroup.findViewById(checkedId);
                String content = checkRadioButton.getText().toString();
                if ("高频".equalsIgnoreCase(content)) {
                    binder.changeTaskFrequency(Constants.GETTESTTASKRATE_HIGHRATE);
                } else if ("演示".equalsIgnoreCase(content)) {
                    binder.changeTaskFrequency(Constants.GETTESTTASKRATE_SHOW);
                } else {//"普通"
                    binder.changeTaskFrequency(Constants.GETTESTTASKRATE_NORMAL);
                }
            }
        });
    }

    ObtainTaskService.taskFrequencyController binder = null;
    private ServiceConnection taskFrequencyConn = new ServiceConnection() {
        // 服务被绑定成功之后执行
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = ((ObtainTaskService.taskFrequencyController) iBinder);
        }

        // 服务奔溃或者被杀掉执行
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binder = null;
        }
    };

    /**
     * 当fragment不可见的时候解绑服务，显示出来重新绑定
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            getActivity().unbindService(taskFrequencyConn);
        } else {
            getActivity().bindService(mIntent, taskFrequencyConn, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 点击重启按钮
             */
            case R.id.reboot_service:
                //判断获取任务的服务是否在运行
                boolean obtainService = SystemUtils.isServiceWork(getActivity(), Constants.PACKAGE_OBTAINTASK);

                //判断上传任务结果的服务是否在运行
                boolean upLoadService = SystemUtils.isServiceWork(getActivity(), Constants.PACKAGE_UPLOADTASK);

                //判断上传手机信息的服务是否在运行
                boolean phoneService = SystemUtils.isServiceWork(getActivity(), Constants.PACKAGE_PHONEINFO);

                if (!obtainService) {
                    Toast.makeText(getActivity(), R.string.service_restart_obtainTask, Toast.LENGTH_SHORT).show();
                    getActivity().startService(new Intent(getActivity(), ObtainTaskService.class));
                }
                if (!upLoadService) {
                    Toast.makeText(getActivity(), R.string.service_restart_task, Toast.LENGTH_SHORT).show();
                    getActivity().startService(new Intent(getActivity(), UploadMachineInfoService.class));
                }
                if (!phoneService) {
                    Toast.makeText(getActivity(), R.string.service_restart_phoneInfo, Toast.LENGTH_SHORT).show();
                    getActivity().startService(new Intent(getActivity(), UploadPhoneInfoService.class));
                }

                if (obtainService == true && upLoadService == true && phoneService == true) {
                    Toast.makeText(getActivity(), R.string.service_normal, Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}

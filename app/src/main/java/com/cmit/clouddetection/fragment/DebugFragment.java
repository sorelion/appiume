package com.cmit.clouddetection.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmit.clouddetection.adapter.DebugRecyclerAdapter;
import com.cmit.clouddetection.databinding.FragmentDebugBinding;
import com.cmit.clouddetection.threadpool.ThreadPools;
import com.cmit.clouddetection.utils.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pact on 2018/9/26.
 */

public class DebugFragment extends Fragment {

    private FragmentDebugBinding mFragmentDebugBinding;
    private int number = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mDebugRecyclerAdapter.notifyDataSetChanged();
                    if (number == 0) {
                        number = 1;
                    }
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            readFile();
                        }
                    }, 1000);
                    break;
                default:
                    break;
            }
        }
    };
    private DebugRecyclerAdapter mDebugRecyclerAdapter;
    private RecyclerView mRvLogcat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentDebugBinding = FragmentDebugBinding.inflate(inflater);
        init();
        return mFragmentDebugBinding.getRoot();
    }


    private void init() {
        mRvLogcat = mFragmentDebugBinding.rvLogcat;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvLogcat.setLayoutManager(linearLayoutManager);
        mDebugRecyclerAdapter = new DebugRecyclerAdapter(getActivity(),mList );
        mRvLogcat.setAdapter(mDebugRecyclerAdapter);
        readFile();
    }

    @Override
    public void onHiddenChanged(final boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            number = 0;
            readFile();
        }
    }

    private List<String> mStrings = new ArrayList<>();
    private List<String> mList = new ArrayList<>();

    public void readFile() {
        mStrings.clear();
        ThreadPools.excute(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    String filePath = LogUtil.pathname + "/" + LogUtil.timeStamp2DateByToday(System.currentTimeMillis() + "") + ".txt";
                    if (new File(filePath).exists()) {
                        FileInputStream fileInputStream = new FileInputStream(filePath);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                        while (true) {
                            String str = bufferedReader.readLine();
                            if (str != null) {
                                mStrings.add(str);
                            } else {
                                break;
                            }
                        }
                        fileInputStream.close();
                    }
                    mList.clear();
                    mList.addAll(mStrings);
                    mHandler.sendEmptyMessage(1);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

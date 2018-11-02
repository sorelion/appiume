package com.cmit.clouddetection.bean;

/**
 * Created by pact on 2018/9/28.
 */

public enum NetworkState {
    NETWORK_NONE("无网络", 0), NETWORK_MOBILE("数据网络", 1), NETWORK_WIFI("wifi网络", 2);
    private String state;
    private int flag;

    public int getState() {
        return flag;
    }

    NetworkState(String state, int i) {
        this.state = state;
        this.flag = i;
    }
}

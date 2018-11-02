package com.cmit.clouddetection.bean;

import java.io.Serializable;

/**
 * Created by pact on 2018/9/26.
 */

public class MachineInfo implements Serializable {


    private static final long serialVersionUID = 7067975805165927664L;

    private String type;//型号
    private String resourceId;//资源编号
    private String imei;//终端标识
    private String imsi;//手机卡标识
    private String phonePosition;//手机位置
    private String connectType;//连接方式（互联网连接，USB连接）
    private Integer electricity;//电量
    private Integer networkType;//网络制式 0无网络 1 移动网络 2WiFi网络
    private Integer appVersion;//App版本
    private String installedSoftInfo;//已安装软件信息

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getPhonePosition() {
        return phonePosition;
    }

    public void setPhonePosition(String phonePosition) {
        this.phonePosition = phonePosition;
    }

    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }


    public String getInstalledSoftInfo() {
        return installedSoftInfo;
    }

    public void setInstalledSoftInfo(String installedSoftInfo) {
        this.installedSoftInfo = installedSoftInfo;
    }

    public int getElectricity() {
        return electricity;
    }

    public void setElectricity(int electricity) {
        this.electricity = electricity;
    }

    public int getNetworkType() {
        return networkType;
    }

    public void setNetworkType(int networkType) {
        this.networkType = networkType;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }
}

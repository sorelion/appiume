package com.cmit.clouddetection.bean;

import java.util.Date;

/**
 * Created by pact on 2018/11/1.
 */

public class SMSInfo {
    private String smsId;
    private String mobile;
    private String content ;
    private String time;
    private Date createTime;//创建数据库时间
    private int type = 0;  //0发送 1接收

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

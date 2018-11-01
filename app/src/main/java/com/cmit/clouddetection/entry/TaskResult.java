package com.cmit.clouddetection.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pact on 2018/10/12.
 */
@Entity
public class TaskResult {
    @Id(autoincrement = true)
    private Long id;
    private String taskSerial;//任务流水号
    private String instanceName;//实例名
    private String scriptId;//脚本Id
    private String operator;//运营商
    private String province;//省份
    private String business;//业务名称
    private String channel;//渠道
    private String beginTime;//开始时间
    private String endTime;//结束时间
    private String taskResult;//任务结果
    private String phoneNum;//电话号码
    @Generated(hash = 722914707)
    public TaskResult(Long id, String taskSerial, String instanceName,
            String scriptId, String operator, String province, String business,
            String channel, String beginTime, String endTime, String taskResult,
            String phoneNum) {
        this.id = id;
        this.taskSerial = taskSerial;
        this.instanceName = instanceName;
        this.scriptId = scriptId;
        this.operator = operator;
        this.province = province;
        this.business = business;
        this.channel = channel;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.taskResult = taskResult;
        this.phoneNum = phoneNum;
    }
    @Generated(hash = 1527756526)
    public TaskResult() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTaskSerial() {
        return this.taskSerial;
    }
    public void setTaskSerial(String taskSerial) {
        this.taskSerial = taskSerial;
    }
    public String getInstanceName() {
        return this.instanceName;
    }
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
    public String getScriptId() {
        return this.scriptId;
    }
    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }
    public String getOperator() {
        return this.operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getBusiness() {
        return this.business;
    }
    public void setBusiness(String business) {
        this.business = business;
    }
    public String getChannel() {
        return this.channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getBeginTime() {
        return this.beginTime;
    }
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getTaskResult() {
        return this.taskResult;
    }
    public void setTaskResult(String taskResult) {
        this.taskResult = taskResult;
    }
    public String getPhoneNum() {
        return this.phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}

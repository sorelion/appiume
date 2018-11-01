package com.cmit.clouddetection.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pact on 2018/10/12.
 */
@Entity
public class TaskResultDetail {
    private Long id;
    private int scriptId;//脚本Id
    private double serialNum;//步骤序号
    private int operateNum;//操作类型
    private String paramValue;//参数值
    private String successKeyword;//成功关键字
    private int isTimeStamp;//时间戳
    private String runningResult;//执行结果
    private String singleStepBeginTime;//开始时间
    private String singleStepEndTime;//结束时间
    private String resultScreenShot;//结果截图
    @Generated(hash = 1199441343)
    public TaskResultDetail(Long id, int scriptId, double serialNum, int operateNum,
            String paramValue, String successKeyword, int isTimeStamp,
            String runningResult, String singleStepBeginTime,
            String singleStepEndTime, String resultScreenShot) {
        this.id = id;
        this.scriptId = scriptId;
        this.serialNum = serialNum;
        this.operateNum = operateNum;
        this.paramValue = paramValue;
        this.successKeyword = successKeyword;
        this.isTimeStamp = isTimeStamp;
        this.runningResult = runningResult;
        this.singleStepBeginTime = singleStepBeginTime;
        this.singleStepEndTime = singleStepEndTime;
        this.resultScreenShot = resultScreenShot;
    }
    @Generated(hash = 1372654216)
    public TaskResultDetail() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getScriptId() {
        return this.scriptId;
    }
    public void setScriptId(int scriptId) {
        this.scriptId = scriptId;
    }
    public double getSerialNum() {
        return this.serialNum;
    }
    public void setSerialNum(double serialNum) {
        this.serialNum = serialNum;
    }
    public int getOperateNum() {
        return this.operateNum;
    }
    public void setOperateNum(int operateNum) {
        this.operateNum = operateNum;
    }
    public String getParamValue() {
        return this.paramValue;
    }
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    public String getSuccessKeyword() {
        return this.successKeyword;
    }
    public void setSuccessKeyword(String successKeyword) {
        this.successKeyword = successKeyword;
    }
    public int getIsTimeStamp() {
        return this.isTimeStamp;
    }
    public void setIsTimeStamp(int isTimeStamp) {
        this.isTimeStamp = isTimeStamp;
    }
    public String getRunningResult() {
        return this.runningResult;
    }
    public void setRunningResult(String runningResult) {
        this.runningResult = runningResult;
    }
    public String getSingleStepBeginTime() {
        return this.singleStepBeginTime;
    }
    public void setSingleStepBeginTime(String singleStepBeginTime) {
        this.singleStepBeginTime = singleStepBeginTime;
    }
    public String getSingleStepEndTime() {
        return this.singleStepEndTime;
    }
    public void setSingleStepEndTime(String singleStepEndTime) {
        this.singleStepEndTime = singleStepEndTime;
    }
    public String getResultScreenShot() {
        return this.resultScreenShot;
    }
    public void setResultScreenShot(String resultScreenShot) {
        this.resultScreenShot = resultScreenShot;
    }


}

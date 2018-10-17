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
    @Id
    @Property(nameInDb = "ID")
    private String scriptId;//脚本Id
    private String serialNum;//步骤序号
    private String operateNum;//操作类型
    private String paramValue;//参数值
    private String successKeyword;//成功关键字
    private String isTimeStamp;//时间戳
    private String runningResult;//执行结果
    private String singleStepBeginTime;//开始时间
    private String singleStepEndTime;//结束时间
    private String resultScreenShot;//结果截图
    @Generated(hash = 171434094)
    public TaskResultDetail(String scriptId, String serialNum, String operateNum,
            String paramValue, String successKeyword, String isTimeStamp,
            String runningResult, String singleStepBeginTime,
            String singleStepEndTime, String resultScreenShot) {
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
    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }
    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getOperateNum() {
        return operateNum;
    }

    public void setOperateNum(String operateNum) {
        this.operateNum = operateNum;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getSuccessKeyword() {
        return successKeyword;
    }

    public void setSuccessKeyword(String successKeyword) {
        this.successKeyword = successKeyword;
    }

    public String getIsTimeStamp() {
        return isTimeStamp;
    }

    public void setIsTimeStamp(String isTimeStamp) {
        this.isTimeStamp = isTimeStamp;
    }

    public String getRunningResult() {
        return runningResult;
    }

    public void setRunningResult(String runningResult) {
        this.runningResult = runningResult;
    }

    public String getSingleStepBeginTime() {
        return singleStepBeginTime;
    }

    public void setSingleStepBeginTime(String singleStepBeginTime) {
        this.singleStepBeginTime = singleStepBeginTime;
    }

    public String getSingleStepEndTime() {
        return singleStepEndTime;
    }

    public void setSingleStepEndTime(String singleStepEndTime) {
        this.singleStepEndTime = singleStepEndTime;
    }

    public String getResultScreenShot() {
        return resultScreenShot;
    }

    public void setResultScreenShot(String resultScreenShot) {
        this.resultScreenShot = resultScreenShot;
    }
}

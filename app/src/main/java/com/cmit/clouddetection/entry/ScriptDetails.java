package com.cmit.clouddetection.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pact on 2018/9/30.
 */
@Entity
public class ScriptDetails {

    private Long id;
    //步骤ID
    private String stepId;
    //步骤序号
    private double serialNum;
    //操作类型
    private int operateType;
    //参数值
    private String paramValue;
    //成功关键字
    private String successKeyword;
    //时间戳
    private int isTimeStamp;//是、否
    @Generated(hash = 1756487211)
    public ScriptDetails(Long id, String stepId, double serialNum, int operateType,
            String paramValue, String successKeyword, int isTimeStamp) {
        this.id = id;
        this.stepId = stepId;
        this.serialNum = serialNum;
        this.operateType = operateType;
        this.paramValue = paramValue;
        this.successKeyword = successKeyword;
        this.isTimeStamp = isTimeStamp;
    }
    @Generated(hash = 1315432966)
    public ScriptDetails() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStepId() {
        return this.stepId;
    }
    public void setStepId(String stepId) {
        this.stepId = stepId;
    }
    public double getSerialNum() {
        return this.serialNum;
    }
    public void setSerialNum(double serialNum) {
        this.serialNum = serialNum;
    }
    public int getOperateType() {
        return this.operateType;
    }
    public void setOperateType(int operateType) {
        this.operateType = operateType;
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



}

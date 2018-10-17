package com.cmit.clouddetection.bean;

/**
 * Created by pact on 2018/10/12.
 */

public class TaskDetailInfo {
    private int id;                 //步骤ID
    private int serialNum;          //步骤序号
    private int operateType;        //操作类型
    private String paramValue;         //参数值
    private String successKeyword;  //成功关键字
    private int isTimestamp;        //时间戳
    private int scriptId;           //父脚本ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
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

    public int getIsTimestamp() {
        return isTimestamp;
    }

    public void setIsTimestamp(int isTimestamp) {
        this.isTimestamp = isTimestamp;
    }

    public int getScriptId() {
        return scriptId;
    }

    public void setScriptId(int scriptId) {
        this.scriptId = scriptId;
    }
}

package com.cmit.clouddetection.bean;

import java.util.List;

/**
 * Created by pact on 2018/10/11.
 */

public class TaskInfo {

    /**
     * id : 5
     * instanceName : 2018-10-09 09:30:00
     * taskStatus : 1
     * terminalResourceMark : null
     * phoneNum : 1008611
     * taskId : null
     * scriptId : 15
     * strategyId : null
     * taskSerial : 1539240849340
     * operator : 1
     * provinceName : 广东
     * businessName : test2
     * channel : 1
     * isAided : null
     * uselocalnum : 0
     * list : [{"id":1,"serialNum":1,"operateType":1,"paramValue":1,"successKeyword":"启动","isTimestamp":1,"scriptId":15},{"id":2,"serialNum":2,"operateType":2,"paramValue":2,"successKeyword":"点击","isTimestamp":1,"scriptId":15},{"id":3,"serialNum":3,"operateType":3,"paramValue":3,"successKeyword":"输入","isTimestamp":1,"scriptId":15},{"id":4,"serialNum":4,"operateType":4,"paramValue":4,"successKeyword":"回退","isTimestamp":1,"scriptId":15},{"id":5,"serialNum":1,"operateType":1,"paramValue":1,"successKeyword":"启动","isTimestamp":1,"scriptId":15}]
     */

    private int id;//
    private String instanceName;
    private int taskStatus;
    private Object terminalResourceMark;
    private String phoneNum;
    private Object taskId;
    private int scriptId;//脚本ID
    private Object strategyId;
    private long taskSerial;
    private int operator;
    private String provinceName;
    private String businessName;
    private int channel;
    private Object isAided;
    private int uselocalnum;
    private List<TaskDetailInfo> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Object getTerminalResourceMark() {
        return terminalResourceMark;
    }

    public void setTerminalResourceMark(Object terminalResourceMark) {
        this.terminalResourceMark = terminalResourceMark;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Object getTaskId() {
        return taskId;
    }

    public void setTaskId(Object taskId) {
        this.taskId = taskId;
    }

    public int getScriptId() {
        return scriptId;
    }

    public void setScriptId(int scriptId) {
        this.scriptId = scriptId;
    }

    public Object getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Object strategyId) {
        this.strategyId = strategyId;
    }

    public long getTaskSerial() {
        return taskSerial;
    }

    public void setTaskSerial(long taskSerial) {
        this.taskSerial = taskSerial;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public Object getIsAided() {
        return isAided;
    }

    public void setIsAided(Object isAided) {
        this.isAided = isAided;
    }

    public int getUselocalnum() {
        return uselocalnum;
    }

    public void setUselocalnum(int uselocalnum) {
        this.uselocalnum = uselocalnum;
    }

    public List<TaskDetailInfo> getList() {
        return list;
    }

    public void setList(List<TaskDetailInfo> list) {
        this.list = list;
    }
}

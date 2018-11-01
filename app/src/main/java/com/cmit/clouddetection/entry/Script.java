package com.cmit.clouddetection.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pact on 2018/9/30.
 */
@Entity
public class Script {
    @Id(autoincrement = true)
    private Long id;
    //脚本ID
    private int scriptId;
    //任务ID
    private int taskId;
    //策略ID
    private int strategyId;
    //运营商
    private int operator;
    //省份
    private String province;
    //业务名称
    private String businessName;
    //智能辅助
    private int aided;//1.不开启使用本机号码  2.不开启使用短信猫号码 3.开启使用本机号码 4.开启使用短信号码
    //实例名
    private String instanceName;
    //手机号码
    private String phoneNum;
    //任务流水号
    private Long taskSerial;
    @Generated(hash = 1908913599)
    public Script(Long id, int scriptId, int taskId, int strategyId, int operator,
            String province, String businessName, int aided, String instanceName,
            String phoneNum, Long taskSerial) {
        this.id = id;
        this.scriptId = scriptId;
        this.taskId = taskId;
        this.strategyId = strategyId;
        this.operator = operator;
        this.province = province;
        this.businessName = businessName;
        this.aided = aided;
        this.instanceName = instanceName;
        this.phoneNum = phoneNum;
        this.taskSerial = taskSerial;
    }
    @Generated(hash = 231174866)
    public Script() {
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
    public int getTaskId() {
        return this.taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public int getStrategyId() {
        return this.strategyId;
    }
    public void setStrategyId(int strategyId) {
        this.strategyId = strategyId;
    }
    public int getOperator() {
        return this.operator;
    }
    public void setOperator(int operator) {
        this.operator = operator;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getBusinessName() {
        return this.businessName;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public int getAided() {
        return this.aided;
    }
    public void setAided(int aided) {
        this.aided = aided;
    }
    public String getInstanceName() {
        return this.instanceName;
    }
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
    public String getPhoneNum() {
        return this.phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public Long getTaskSerial() {
        return this.taskSerial;
    }
    public void setTaskSerial(Long taskSerial) {
        this.taskSerial = taskSerial;
    }

}

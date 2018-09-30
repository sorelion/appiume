package com.cmit.clouddetection.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pact on 2018/9/30.
 */
@Entity
public class Script {
    @Id
    @Property(nameInDb = "ID")
    private Long id;
    //脚本ID
    private Long scriptId;
    //任务ID
    private String taskId;
    //策略ID
    private String strategyId;
    //运营商
    private String operator;
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
    private String taskSerial;

    @Generated(hash = 1416229546)
    public Script(Long id, Long scriptId, String taskId, String strategyId,
            String operator, String province, String businessName, int aided,
            String instanceName, String phoneNum, String taskSerial) {
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

    public Long getScriptId() {
        return scriptId;
    }

    public void setScriptId(Long scriptId) {
        this.scriptId = scriptId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getAided() {
        return aided;
    }

    public void setAided(int aided) {
        this.aided = aided;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTaskSerial() {
        return taskSerial;
    }

    public void setTaskSerial(String taskSerial) {
        this.taskSerial = taskSerial;
    }
}

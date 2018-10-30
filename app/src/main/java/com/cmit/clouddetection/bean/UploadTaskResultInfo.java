package com.cmit.clouddetection.bean;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 任务结果信息
 * Created by mls on 2018/10/11.
 */

public class UploadTaskResultInfo implements Serializable {
    private static final long serialVersionUID = -6641982997906535389L;

    // 实例名
    private String instanceName;

    // 任务流水
    private Long taskSerial;

    // 任务id
    private Integer taskId;

    // 脚本id
    private Integer scriptId;

    // 运营商(1.中国移动、2.中国联通、3.中国电信)
    private Integer operator;

    // 省份
    private String provinceName;

    // 业务名称
    private String businessName;

    // 渠道(1.集团APP，2.省APP，3.短厅)
    private Integer channel;

    // 策略id
    private Integer stratrgyId;

    // 开始时间
    private Date beginTime;

    // 结束时间
    private Date endTime;

    // 计时步骤开始时间
    private Date timingBeginTime;

    // 计时步骤结束时间
    private Date timingEndTime;

    // 耗时(计时步骤开始时间-计时步骤结束时间)
    private Integer timeUse;

    // 任务结果(1.成功，2.失败，3.任务超时)
    private Integer taskContent;

    // 结果明细
    private String resultDetail;

    // 手机号码
    private String phoneNum;

    // 步骤结果明细
    private List<TaskResultDetailInfo> list;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Long getTaskSerial() {
        return taskSerial;
    }

    public void setTaskSerial(Long taskSerial) {
        this.taskSerial = taskSerial;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getScriptId() {
        return scriptId;
    }

    public void setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
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

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getStratrgyId() {
        return stratrgyId;
    }

    public void setStratrgyId(Integer stratrgyId) {
        this.stratrgyId = stratrgyId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getTimingBeginTime() {
        return timingBeginTime;
    }

    public void setTimingBeginTime(Date timingBeginTime) {
        this.timingBeginTime = timingBeginTime;
    }

    public Date getTimingEndTime() {
        return timingEndTime;
    }

    public void setTimingEndTime(Date timingEndTime) {
        this.timingEndTime = timingEndTime;
    }

    public Integer getTimeUse() {
        return timeUse;
    }

    public void setTimeUse(Integer timeUse) {
        this.timeUse = timeUse;
    }

    public Integer getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(Integer taskContent) {
        this.taskContent = taskContent;
    }

    public String getResultDetail() {
        return resultDetail;
    }

    public void setResultDetail(String resultDetail) {
        this.resultDetail = resultDetail;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<TaskResultDetailInfo> getList() {
        return list;
    }

    public void setList(List<TaskResultDetailInfo> list) {
        this.list = list;
    }


}

package com.cmit.clouddetection.bean;



import java.io.Serializable;
import java.util.Date;

/**
 * 任务结果明细
 * Created by mls on 2018/10/11.
 */

public class TaskResultDetailInfo implements Serializable {
    private static final long serialVersionUID = -2938847083328920489L;

    // 结果明细id
    private Integer detailId;

    // 结果id
    private Integer resultId;

    // 步骤id
    private Integer stepId;

    // 步骤序号
    private float serialNum;

    // 操作类型(1.启动APP，2.点击，3.输入，4.回退，5.截图，6.滑动,7.短信验证码，8.关闭APP)
    private Integer operateType;

    // 参数值
    private String paramValue;

    // 成功关键字
    private String successKeyword;

    // 时间戳(1.是，2.否)
    private Integer isTimestamp;

    // 执行结果(1.成功，0.失败)
    private Integer runningResult;

    // 单个步骤开始时间
    private String singleStepBeginTime;

    // 单个步骤结束时间
    private String singleStepEndTime;

    // 结果截图(URL方式保存截图)
    private String resultScreenshot;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public float getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(float serialNum) {
        this.serialNum = serialNum;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
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

    public Integer getIsTimestamp() {
        return isTimestamp;
    }

    public void setIsTimestamp(Integer isTimestamp) {
        this.isTimestamp = isTimestamp;
    }

    public Integer getRunningResult() {
        return runningResult;
    }

    public void setRunningResult(Integer runningResult) {
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

    public String getResultScreenshot() {
        return resultScreenshot;
    }

    public void setResultScreenshot(String resultScreenshot) {
        this.resultScreenshot = resultScreenshot;
    }
}

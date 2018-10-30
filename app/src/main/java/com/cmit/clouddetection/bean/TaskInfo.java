package com.cmit.clouddetection.bean;

import java.util.List;

/**
 * Created by pact on 2018/10/11.
 */

public class TaskInfo {


    /**
     * code : 0
     * msg : 操作成功!
     * data : {"id":1,"instanceName":"2018-10-17 12:00:00","taskStatus":1,"terminalResourceMark":null,"phoneNum":null,"taskId":1,"scriptId":1,"strategyId":1,"taskSerial":1540281098753,"operator":1,"provinceName":"广东","businessName":"流量查询","channel":1,"isAided":null,"uselocalnum":0,"smsVerifycodeConfigs":[],"scriptInfos":[{"id":1,"serialNum":1,"operateType":1,"paramValue":"ChinaMobile","successKeyword":"","isTimestamp":null,"scriptId":1},{"id":2,"serialNum":2,"operateType":2,"paramValue":"我的","successKeyword":"","isTimestamp":null,"scriptId":1},{"id":3,"serialNum":3,"operateType":2,"paramValue":"我的流量","successKeyword":"流量","isTimestamp":null,"scriptId":1}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * instanceName : 2018-10-17 12:00:00
         * taskStatus : 1
         * terminalResourceMark : null
         * phoneNum : null
         * taskId : 1
         * scriptId : 1
         * strategyId : 1
         * taskSerial : 1540281098753
         * operator : 1
         * provinceName : 广东
         * businessName : 流量查询
         * channel : 1
         * isAided : null
         * uselocalnum : 0
         * smsVerifycodeConfigs : []
         * scriptInfos : [{"id":1,"serialNum":1,"operateType":1,"paramValue":"ChinaMobile","successKeyword":"","isTimestamp":null,"scriptId":1},{"id":2,"serialNum":2,"operateType":2,"paramValue":"我的","successKeyword":"","isTimestamp":null,"scriptId":1},{"id":3,"serialNum":3,"operateType":2,"paramValue":"我的流量","successKeyword":"流量","isTimestamp":null,"scriptId":1}]
         */

        private int id;
        private String instanceName;
        private int taskStatus;
        private Object terminalResourceMark;
        private Object phoneNum;
        private int taskId;
        private int scriptId;
        private int strategyId;
        private long taskSerial;
        private int operator;
        private String provinceName;
        private String businessName;
        private int channel;
        private Object isAided;
        private int uselocalnum;
        private List<?> smsVerifycodeConfigs;
        private List<ScriptInfosBean> scriptInfos;

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

        public Object getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(Object phoneNum) {
            this.phoneNum = phoneNum;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getScriptId() {
            return scriptId;
        }

        public void setScriptId(int scriptId) {
            this.scriptId = scriptId;
        }

        public int getStrategyId() {
            return strategyId;
        }

        public void setStrategyId(int strategyId) {
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

        public List<?> getSmsVerifycodeConfigs() {
            return smsVerifycodeConfigs;
        }

        public void setSmsVerifycodeConfigs(List<?> smsVerifycodeConfigs) {
            this.smsVerifycodeConfigs = smsVerifycodeConfigs;
        }

        public List<ScriptInfosBean> getScriptInfos() {
            return scriptInfos;
        }

        public void setScriptInfos(List<ScriptInfosBean> scriptInfos) {
            this.scriptInfos = scriptInfos;
        }

        public static class ScriptInfosBean {
            /**
             * id : 1
             * serialNum : 1
             * operateType : 1
             * paramValue : ChinaMobile
             * successKeyword :
             * isTimestamp : null
             * scriptId : 1
             */

            private int id;
            private int serialNum;
            private int operateType;
            private String paramValue;
            private String successKeyword;
            private Object isTimestamp;
            private int scriptId;

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

            public Object getIsTimestamp() {
                return isTimestamp;
            }

            public void setIsTimestamp(Object isTimestamp) {
                this.isTimestamp = isTimestamp;
            }

            public int getScriptId() {
                return scriptId;
            }

            public void setScriptId(int scriptId) {
                this.scriptId = scriptId;
            }
        }
    }
}

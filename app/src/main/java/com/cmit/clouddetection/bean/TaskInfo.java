package com.cmit.clouddetection.bean;

import java.util.List;

/**
 * Created by pact on 2018/10/11.
 */

public class TaskInfo {


    /**
     * code : 0
     * msg : 操作成功!
     * data : {"id":1,"instanceName":"2018-10-17 12:00:00","phoneNum":"13733153625","taskId":1,"scriptId":1,"strategyId":1,"taskSerial":1540976424359,"operator":1,"provinceName":"河南","businessName":"流量查询","channel":1,"isAided":1,"useLocalNum":0,"smsVerifycodeConfigs":[{"id":1,"keyword":"短信验证码是：","length":6}],"scriptInfos":[{"id":6,"serialNum":0.5,"operateType":2,"paramValue":"com.greenpoint.android.mc10086.activity","successKeyword":null,"isTimestamp":0,"scriptId":1},{"id":1,"serialNum":1,"operateType":1,"paramValue":"com.greenpoint.android.mc10086.activity/com.leadeon.cmcc.base.StartPageActivity","successKeyword":"","isTimestamp":0,"scriptId":1},{"id":5,"serialNum":1.1,"operateType":10,"paramValue":"5","successKeyword":"","isTimestamp":0,"scriptId":1},{"id":13,"serialNum":1.2,"operateType":6,"paramValue":null,"successKeyword":null,"isTimestamp":0,"scriptId":1},{"id":2,"serialNum":2,"operateType":3,"paramValue":"我的","successKeyword":"","isTimestamp":0,"scriptId":1},{"id":3,"serialNum":3,"operateType":10,"paramValue":"2","successKeyword":"","isTimestamp":0,"scriptId":1},{"id":4,"serialNum":4,"operateType":3,"paramValue":"流量管家","successKeyword":"","isTimestamp":0,"scriptId":1},{"id":7,"serialNum":5,"operateType":10,"paramValue":"3","successKeyword":null,"isTimestamp":0,"scriptId":1},{"id":8,"serialNum":6,"operateType":4,"paramValue":"resourceId=com.greenpoint.android.mc10086.activity:id/user_phoneno_edt","successKeyword":"#phoneno","isTimestamp":0,"scriptId":1},{"id":9,"serialNum":7,"operateType":3,"paramValue":"获取验证码","successKeyword":null,"isTimestamp":0,"scriptId":1},{"id":10,"serialNum":8,"operateType":8,"paramValue":"resourceId=com.greenpoint.android.mc10086.activity:id/user_login_smspassword_edt","successKeyword":"1","isTimestamp":0,"scriptId":1},{"id":11,"serialNum":9,"operateType":3,"paramValue":"登录","successKeyword":"流量总量>>剩余 ","isTimestamp":1,"scriptId":1},{"id":12,"serialNum":10,"operateType":9,"paramValue":"com.greenpoint.android.mc10086.activity","successKeyword":null,"isTimestamp":0,"scriptId":1}]}
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
         * phoneNum : 13733153625
         * taskId : 1
         * scriptId : 1
         * strategyId : 1
         * taskSerial : 1540976424359
         * operator : 1
         * provinceName : 河南
         * businessName : 流量查询
         * channel : 1
         * isAided : 1
         * useLocalNum : 0
         * smsVerifycodeConfigs : [{"id":1,"keyword":"短信验证码是：","length":6}]
         * scriptInfos : [{"id":6,"serialNum":0.5,"operateType":2,"paramValue":"com.greenpoint.android.mc10086.activity","successKeyword":null,"isTimestamp":0,"scriptId":1},{"id":1,"serialNum":1,"operateType":1,"paramValue":"com.greenpoint.android.mc10086.activity/com.leadeon.cmcc.base.StartPageActivity","successKeyword":"","isTimestamp":0,"scriptId":1},{"id":5,"serialNum":1.1,"operateType":10,"paramValue":"5","successKeyword":"","isTimestamp":0,"scriptId":1},{"id":13,"serialNum":1.2,"operateType":6,"paramValue":null,"successKeyword":null,"isTimestamp":0,"scriptId":1},{"id":2,"serialNum":2,"operateType":3,"paramValue":"我的","successKeyword":"","isTimestamp":0,"scriptId":1},{"id":3,"serialNum":3,"operateType":10,"paramValue":"2","successKeyword":"","isTimestamp":0,"scriptId":1},{"id":4,"serialNum":4,"operateType":3,"paramValue":"流量管家","successKeyword":"","isTimestamp":0,"scriptId":1},{"id":7,"serialNum":5,"operateType":10,"paramValue":"3","successKeyword":null,"isTimestamp":0,"scriptId":1},{"id":8,"serialNum":6,"operateType":4,"paramValue":"resourceId=com.greenpoint.android.mc10086.activity:id/user_phoneno_edt","successKeyword":"#phoneno","isTimestamp":0,"scriptId":1},{"id":9,"serialNum":7,"operateType":3,"paramValue":"获取验证码","successKeyword":null,"isTimestamp":0,"scriptId":1},{"id":10,"serialNum":8,"operateType":8,"paramValue":"resourceId=com.greenpoint.android.mc10086.activity:id/user_login_smspassword_edt","successKeyword":"1","isTimestamp":0,"scriptId":1},{"id":11,"serialNum":9,"operateType":3,"paramValue":"登录","successKeyword":"流量总量>>剩余 ","isTimestamp":1,"scriptId":1},{"id":12,"serialNum":10,"operateType":9,"paramValue":"com.greenpoint.android.mc10086.activity","successKeyword":null,"isTimestamp":0,"scriptId":1}]
         */

        private int id;
        private String instanceName;
        private String phoneNum;
        private int taskId;
        private int scriptId;
        private int strategyId;
        private long taskSerial;
        private int operator;
        private String provinceName;
        private String businessName;
        private int channel;
        private int isAided;
        private int useLocalNum;
        private List<SmsVerifycodeConfigsBean> smsVerifycodeConfigs;
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

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
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

        public int getIsAided() {
            return isAided;
        }

        public void setIsAided(int isAided) {
            this.isAided = isAided;
        }

        public int getUseLocalNum() {
            return useLocalNum;
        }

        public void setUseLocalNum(int useLocalNum) {
            this.useLocalNum = useLocalNum;
        }

        public List<SmsVerifycodeConfigsBean> getSmsVerifycodeConfigs() {
            return smsVerifycodeConfigs;
        }

        public void setSmsVerifycodeConfigs(List<SmsVerifycodeConfigsBean> smsVerifycodeConfigs) {
            this.smsVerifycodeConfigs = smsVerifycodeConfigs;
        }

        public List<ScriptInfosBean> getScriptInfos() {
            return scriptInfos;
        }

        public void setScriptInfos(List<ScriptInfosBean> scriptInfos) {
            this.scriptInfos = scriptInfos;
        }

        public static class SmsVerifycodeConfigsBean {
            /**
             * id : 1
             * keyword : 短信验证码是：
             * length : 6
             */

            private int id;
            private String keyword;
            private int length;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }
        }

        public static class ScriptInfosBean {
            /**
             * id : 6
             * serialNum : 0.5
             * operateType : 2
             * paramValue : com.greenpoint.android.mc10086.activity
             * successKeyword : null
             * isTimestamp : 0
             * scriptId : 1
             */

            private int id;
            private double serialNum;
            private int operateType;
            private String paramValue;
            private String successKeyword;
            private int isTimestamp;
            private int scriptId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getSerialNum() {
                return serialNum;
            }

            public void setSerialNum(double serialNum) {
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
    }
}

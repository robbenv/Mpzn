package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/10/8.
 */
public class BbStaticEntity {

    /**
     * code : 200
     * message : success
     * data : {"maxsubject":"虹桥天街","num":4,"total":4,"success":0,"failure":4}
     */

    private int code;
    private String message;
    /**
     * maxsubject : 虹桥天街
     * num : 4
     * total : 4
     * success : 0
     * failure : 4
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String maxsubject;
        private int num;
        private int total;
        private int success;
        private int failure;

        public String getMaxsubject() {
            return maxsubject;
        }

        public void setMaxsubject(String maxsubject) {
            this.maxsubject = maxsubject;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public int getFailure() {
            return failure;
        }

        public void setFailure(int failure) {
            this.failure = failure;
        }
    }
}

package com.mpzn.mpzn.entity;

/**
 * Created by larry on 16-12-2.
 */

public class BbStaticForComEntity {

    /**
     * code : 200
     * message : success
     * data : {"name":"jessie","maxsubject":"虹桥天街","num":16,"total":17,"success":2,"failure":15}
     */

    private int code;
    private String message;
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
        /**
         * name : jessie
         * maxsubject : 虹桥天街
         * num : 16
         * total : 17
         * success : 2
         * failure : 15
         */

        private String name;
        private String maxsubject;
        private int num;
        private int total;
        private int success;
        private int failure;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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

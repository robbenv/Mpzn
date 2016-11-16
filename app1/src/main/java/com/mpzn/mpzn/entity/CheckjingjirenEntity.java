package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/10/14.
 */
public class CheckjingjirenEntity {

    /**
     * code : 200
     * message : 可以进行添加
     * data : {"mid":1327,"xingming":"孙响"}
     */

    private int code;
    private String message;
    /**
     * mid : 1327
     * xingming : 孙响
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
        private int mid;
        private String xingming;

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getXingming() {
            return xingming;
        }

        public void setXingming(String xingming) {
            this.xingming = xingming;
        }
    }
}

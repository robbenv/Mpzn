package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/9/26.
 */

public class CheckStarEntity  {


    /**
     * code : 200
     * message : success
     * data : {"cid":205}
     */

    private int code;
    private String message;
    /**
     * cid : 205
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
        private int cid;
        private String hint;

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }
    }
}

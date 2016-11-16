package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/9/22.
 */

public class SimpleEntity {

    /**
     * code : 200
     * message : success
     * data : {"hint":"报备添加成功"}
     */

    private int code;
    private String message;
    /**
     * hint : 报备添加成功
     */

    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String hint;

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }
    }
}

package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/9/22.
 */

public class AlterPassEntity {

    /**
     * code : 403
     * message : error
     * data : {"hint":"token认证失败"}
     */

    private int code;
    private String message;
    /**
     * hint : token认证失败
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
        private String hint;
        private String token;

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

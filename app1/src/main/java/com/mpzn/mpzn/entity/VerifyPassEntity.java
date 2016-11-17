package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/9/22.
 */

public class VerifyPassEntity {

    /**
     * code : 406
     * message : error
     * data : {"hint":"密码验证失败，请检查原始密码是否正确"}
     */

    private int code;
    private String message;
    /**
     * hint : 密码验证失败，请检查原始密码是否正确
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

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }


    }
}

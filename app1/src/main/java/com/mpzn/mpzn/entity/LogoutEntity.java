package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/9/14.
 */
public class LogoutEntity {

    /**
     * code : 200
     * message : success
     * data : {"hint":"注销成功"}
     */

    private int code;
    private String message;
    /**
     * hint : 注销成功
     */

    private DataEntity data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String hint;

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getHint() {
            return hint;
        }
    }
}

package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/10/18.
 */
public class VersionCheck {


    /**
     * code : 200
     * message : success
     * data : {"version":"1.0.3","upload_time":211111111,"update_content":"211212","soft_address":"http://mpzn8.mpzn.com/package/android/mpzn_103.apk"}
     */

    private int code;
    private String message;
    /**
     * version : 1.0.3
     * upload_time : 211111111
     * update_content : 211212
     * soft_address : http://mpzn8.mpzn.com/package/android/mpzn_103.apk
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
        private String version;
        private int upload_time;
        private String update_content;
        private String soft_address;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getUpload_time() {
            return upload_time;
        }

        public void setUpload_time(int upload_time) {
            this.upload_time = upload_time;
        }

        public String getUpdate_content() {
            return update_content;
        }

        public void setUpdate_content(String update_content) {
            this.update_content = update_content;
        }

        public String getSoft_address() {
            return soft_address;
        }

        public void setSoft_address(String soft_address) {
            this.soft_address = soft_address;
        }
    }
}

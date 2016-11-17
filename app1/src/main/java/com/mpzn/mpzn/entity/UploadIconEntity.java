package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/11/9.
 */

public class UploadIconEntity  {

    /**
     * code : 200
     * message : 上传成功
     * data : {"headimage":"http://appi.mpzn.com/userfiles/image/20161109/asascevsdvas.jpg"}
     */

    private int code;
    private String message;
    /**
     * headimage : http://appi.mpzn.com/userfiles/image/20161109/asascevsdvas.jpg
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
        private String headimage;

        public String getHeadimage() {
            return headimage;
        }

        public void setHeadimage(String headimage) {
            this.headimage = headimage;
        }
    }
}

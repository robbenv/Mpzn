package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/11/14.
 */
public class KfsOwnBuilding {

    /**
     * code : 200
     * message : success
     * data : [{"aid":2873,"subject":"旭辉纯真中心"},{"aid":3914,"subject":"虹桥天街"},{"aid":4826,"subject":"城开中心"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * aid : 2873
         * subject : 旭辉纯真中心
         */

        private int aid;
        private String subject;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }
}

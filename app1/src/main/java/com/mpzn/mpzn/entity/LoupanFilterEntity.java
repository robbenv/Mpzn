package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by larry on 16-12-2.
 */

public class LoupanFilterEntity {

    /**
     * code : 200
     * message : success
     * data : {"loupans":[{"aid":3914,"subject":"虹桥天街"},{"aid":233,"subject":"远雄徐汇园"},{"aid":1518,"subject":"徐汇公园道壹号"},{"aid":1932,"subject":"中洲君廷"}]}
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
        private List<LoupansBean> loupans;

        public List<LoupansBean> getLoupans() {
            return loupans;
        }

        public void setLoupans(List<LoupansBean> loupans) {
            this.loupans = loupans;
        }

        public static class LoupansBean {
            /**
             * aid : 3914
             * subject : 虹桥天街
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
}

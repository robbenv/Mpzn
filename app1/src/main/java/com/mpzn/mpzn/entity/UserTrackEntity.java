package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by larry on 16-12-6.
 */

public class UserTrackEntity {

    /**
     * code : 200
     * message : success
     * data : [{"cid":1413,"khname":"孙响","khphone":"13600004972","subject":"虹桥天街","jdaotime":1477470212},{"cid":1414,"khname":"胡佳","khphone":"13600004972","subject":"虹桥天街","jdaotime":1477471628}]
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
         * cid : 1413
         * khname : 孙响
         * khphone : 13600004972
         * subject : 虹桥天街
         * jdaotime : 1477470212
         */

        private int cid;
        private String khname;
        private String khphone;
        private String subject;
        private int jdaotime;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getKhname() {
            return khname;
        }

        public void setKhname(String khname) {
            this.khname = khname;
        }

        public String getKhphone() {
            return khphone;
        }

        public void setKhphone(String khphone) {
            this.khphone = khphone;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getJdaotime() {
            return jdaotime;
        }

        public void setJdaotime(int jdaotime) {
            this.jdaotime = jdaotime;
        }
    }
}

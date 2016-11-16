package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/10/11.
 */
public class ApplyForSellList {

    /**
     * code : 200
     * message : succcess
     * data : [{"aid":3914,"subject":"虹桥天街","hetongwenben":"","area":"闵行","createdate":1451456163,"updatedate":1452137032},{"aid":133,"subject":"招商花园城","hetongwenben":"","area":"宝山","createdate":1451456163,"updatedate":1452137032},{"aid":35,"subject":"鑫塔水尚","hetongwenben":"userfiles/file/20160318/18163231668d30c6566153.doc","area":"青浦","createdate":1451456163,"updatedate":1452137032}]
     */

    private int code;
    private String message;
    /**
     * aid : 3914
     * subject : 虹桥天街
     * hetongwenben :
     * area : 闵行
     * createdate : 1451456163
     * updatedate : 1452137032
     */

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
        private int aid;
        private String subject;
        private String hetongwenben;
        private String area;
        private int createdate;
        private int updatedate;

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

        public String getHetongwenben() {
            return hetongwenben;
        }

        public void setHetongwenben(String hetongwenben) {
            this.hetongwenben = hetongwenben;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getCreatedate() {
            return createdate;
        }

        public void setCreatedate(int createdate) {
            this.createdate = createdate;
        }

        public int getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(int updatedate) {
            this.updatedate = updatedate;
        }
    }
}

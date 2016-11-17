package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/9/27.
 */

public class MyBBEntity {


    /**
     * code : 200
     * message : succcess
     * data : [{"cid":1416,"subject":"远雄徐汇园","khphone":"13300006699","checked":1,"jdaotime":0,"baobeitime":1477553571,"khname":"哈啊哈","code":25493},{"cid":1412,"subject":"雅居乐公寓","khphone":"13600004972","checked":1,"jdaotime":0,"baobeitime":1477379194,"khname":"孙响响","code":90590}]
     */

    private int code;
    private String message;
    /**
     * cid : 1416
     * subject : 远雄徐汇园
     * khphone : 13300006699
     * checked : 1
     * jdaotime : 0
     * baobeitime : 1477553571
     * khname : 哈啊哈
     * code : 25493
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
        private int cid;
        private String subject;
        private String khphone;
        private int checked;
        private int jdaotime;
        private int baobeitime;
        private String khname;
        private int code;

        public DataBean(boolean isNoData) {
            this.isNoData = isNoData;
        }

        private boolean isNoData=false;

        private boolean isNoData(){
            return isNoData;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getKhphone() {
            return khphone;
        }

        public void setKhphone(String khphone) {
            this.khphone = khphone;
        }

        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public int getJdaotime() {
            return jdaotime;
        }

        public void setJdaotime(int jdaotime) {
            this.jdaotime = jdaotime;
        }

        public int getBaobeitime() {
            return baobeitime;
        }

        public void setBaobeitime(int baobeitime) {
            this.baobeitime = baobeitime;
        }

        public String getKhname() {
            return khname;
        }

        public void setKhname(String khname) {
            this.khname = khname;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}

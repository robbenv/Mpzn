package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/9/22.
 */

public class AddBBMsgEntity {


    /**
     * code : 200
     * message : success
     * data : {"name":"经纪人","phone":"13601924972","companyName":"上海万凯投资管理有限公司","loupans":[{"aid":4850,"subject":"雅居乐公寓"},{"aid":4900,"subject":"珑璟公馆"}]}
     */

    private int code;
    private String message;
    /**
     * name : 经纪人
     * phone : 13601924972
     * companyName : 上海万凯投资管理有限公司
     * loupans : [{"aid":4850,"subject":"雅居乐公寓"},{"aid":4900,"subject":"珑璟公馆"}]
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
        private String name;
        private String phone;
        private String companyName;
        /**
         * aid : 4850
         * subject : 雅居乐公寓
         */

        private List<LoupansBean> loupans;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public List<LoupansBean> getLoupans() {
            return loupans;
        }

        public void setLoupans(List<LoupansBean> loupans) {
            this.loupans = loupans;
        }

        public static class LoupansBean {
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

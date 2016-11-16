package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/10/28.
 */
public class CheckJJRComEntity {

    /**
     * code : 1235
     * message : 有经纪公司，未审核
     * data : {"company_name":"鎏翔投资"}
     */

    private int code;
    private String message;
    /**
     * company_name : 鎏翔投资
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
        private String company_name;

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }
    }
}

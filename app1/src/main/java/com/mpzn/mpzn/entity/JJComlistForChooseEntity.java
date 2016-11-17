package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/10/24.
 */
public class JJComlistForChooseEntity {

    /**
     * code : 200
     * message : success
     * data : [{"mid":9,"company_name":"上海XX地产经纪有限公司"},{"mid":49,"company_name":"上海万凯投资管理有限公司"},{"mid":79,"company_name":"房上房1"},{"mid":80,"company_name":"房上房2"},{"mid":93,"company_name":"上海旭望投资管理"},{"mid":207,"company_name":"上海立家房地产经纪事务所"}]
     */

    private int code;
    private String message;
    /**
     * mid : 9
     * company_name : 上海XX地产经纪有限公司
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
        private int mid;
        private String company_name;

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }
    }
}

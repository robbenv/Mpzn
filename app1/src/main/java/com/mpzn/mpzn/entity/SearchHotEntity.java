package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/9/19.
 */
public class SearchHotEntity {

    /**
     * code : 200
     * message : success
     * data : [{"subject":"华侨城苏河湾塔尖住宅","aid":327},{"subject":"泰禾红桥","aid":1641},{"subject":"鑫苑壹品世家","aid":1722},{"subject":"虹桥金汇紫薇苑","aid":1944},{"subject":"长宁88金廷","aid":241},{"subject":"恒文星尚湾","aid":1788},{"subject":"御沁园公寓","aid":1578},{"subject":"金融家","aid":2006}]
     */

    private int code;
    private String message;
    /**
     * subject : 华侨城苏河湾塔尖住宅
     * aid : 327
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
        private String subject;
        private int aid;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }
    }
}

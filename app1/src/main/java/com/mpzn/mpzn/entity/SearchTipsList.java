package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/9/12.
 */
public class SearchTipsList {

    /**
     * code : 200
     * message : success
     * data : [{"zxcd":1,"title":"待售","diqu":"浦东","subject":"御沁园公寓","aid":1578},{"zxcd":1,"title":"待售","diqu":"宝山","subject":"中环国际公寓","aid":1583},{"zxcd":1,"title":"现房","diqu":"浦东","subject":"金融大学学生公寓","aid":2127},{"zxcd":4,"title":"现房","diqu":"松江","subject":"雅居乐公寓","aid":4850},{"zxcd":1,"title":"现房","diqu":"嘉定","subject":"通用国际酒店式公寓","aid":4891}]
     */

    private int code;
    private String message;
    /**
     * zxcd : 1
     * title : 待售
     * diqu : 浦东
     * subject : 御沁园公寓
     * aid : 1578
     */

    private List<DataEntity> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String zxcd;
        private String title;
        private String diqu;
        private String subject;
        private int aid;

        // 暂无数据属性
        private boolean isNoData = false;
        public boolean isNoData() {
            return isNoData;
        }

        public void setNoData(boolean noData) {
            isNoData = noData;
        }


        public void setZxcd(String zxcd) {
            this.zxcd = zxcd;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDiqu(String diqu) {
            this.diqu = diqu;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getZxcd() {
            return zxcd;
        }

        public String getTitle() {
            return title;
        }

        public String getDiqu() {
            return diqu;
        }

        public String getSubject() {
            return subject;
        }

        public int getAid() {
            return aid;
        }
    }
}

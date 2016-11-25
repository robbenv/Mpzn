package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by larry on 16-11-22.
 */

public class ProxySellListEntity {

    /**
     * code : 200
     * message : succcess
     * data : [{"cid":16,"subject":"天和湖滨别墅","thumb":"userfiles/image/20160620/201354070caa2b263d7533_200_150.jpg","price":88000,"createdate":1476163443,"checked":0,"status":0,"company":"鎏翔投资","area":"浦东","type":"别墅","salestatus":"待售"},{"cid":9,"subject":"通用国际酒店式公寓","thumb":"userfiles/image/20160620/20115502421eb414409172_200_150.jpg","price":88000,"createdate":1476163420,"checked":0,"status":0,"company":"鎏翔投资","area":"嘉定","type":"商住","salestatus":"现房"}]
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "isNoData=" + isNoData +
                    ", cid=" + cid +
                    ", subject='" + subject + '\'' +
                    ", thumb='" + thumb + '\'' +
                    ", price=" + price +
                    ", createdate=" + createdate +
                    ", checked=" + checked +
                    ", status=" + status +
                    ", company='" + company + '\'' +
                    ", area='" + area + '\'' +
                    ", type='" + type + '\'' +
                    ", salestatus='" + salestatus + '\'' +
                    '}';
        }

        private final boolean isNoData;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        /**
         * cid : 16
         * subject : 天和湖滨别墅
         * thumb : userfiles/image/20160620/201354070caa2b263d7533_200_150.jpg
         * price : 88000
         * createdate : 1476163443
         * checked : 0
         * status : 0
         * company : 鎏翔投资
         * area : 浦东
         * type : 别墅
         * salestatus : 待售
         */

        private int aid;
        private int cid;
        private String subject;
        private String thumb;
        private int price;
        private int createdate;
        private int checked;
        private int status;
        private String company;
        private String area;
        private String type;
        private String salestatus;

        public DataBean(boolean isNoData) {
            this.isNoData = isNoData;
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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCreatedate() {
            return createdate;
        }

        public void setCreatedate(int createdate) {
            this.createdate = createdate;
        }

        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSalestatus() {
            return salestatus;
        }

        public void setSalestatus(String salestatus) {
            this.salestatus = salestatus;
        }


        public boolean isNoData() {
            return isNoData;
        }
    }
}

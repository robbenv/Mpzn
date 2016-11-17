package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/10/11.
 */
public class MySellListEntity {

    /**
     * code : 200
     * message : succcess
     * data : [{"subject":"中洲里程","thumb":"userfiles/image/20160128/28155436c7eafaac223197_200_150.jpg","price":88000,"kfsname":"中洲控股(上海) 上海深长城地产有限公司","createdate":1476163420,"checked":0,"salestatus":"期房","status":0,"area":"浦东","type":"住宅"},{"subject":"通用国际酒店式公寓","thumb":"userfiles/image/20160620/20115502421eb414409172_200_150.jpg","price":88000,"kfsname":"上海通昱置地有限公司","createdate":1476163420,"checked":0,"salestatus":"现房","status":0,"area":"嘉定","type":"商住"}]
     */

    private int code;
    private String message;
    /**
     * subject : 中洲里程
     * thumb : userfiles/image/20160128/28155436c7eafaac223197_200_150.jpg
     * price : 88000
     * kfsname : 中洲控股(上海) 上海深长城地产有限公司
     * createdate : 1476163420
     * checked : 0
     * salestatus : 期房
     * status : 0
     * area : 浦东
     * type : 住宅
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
        private String thumb;
        private int price;
        private String kfsname;
        private int createdate;
        private int checked;
        private String salestatus;
        private int status;
        private String area;
        private String type;

        public DataBean(boolean isNoData) {
            this.isNoData = isNoData;
        }

        private boolean isNoData=false;

        private boolean isNoData(){
            return isNoData;
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

        public String getKfsname() {
            return kfsname;
        }

        public void setKfsname(String kfsname) {
            this.kfsname = kfsname;
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

        public String getSalestatus() {
            return salestatus;
        }

        public void setSalestatus(String salestatus) {
            this.salestatus = salestatus;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
    }
}

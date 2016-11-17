package com.mpzn.mpzn.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Icy on 2016/9/26.
 */

public class StarBuildingEntity {

    /**
     * code : 200
     * message : success
     * data : [{"collectid":167,"loupanid":35,"subject":"鑫塔水尚","thumb":"userfiles/image/20151225/25164045ceb902ebcc3061_200_150.jpg","abstract":"鑫塔水尚","price":25000,"area":"青浦","createdate":1474876845,"type":"住宅"},{"collectid":166,"loupanid":35,"subject":"鑫塔水尚","thumb":"userfiles/image/20151225/25164045ceb902ebcc3061_200_150.jpg","abstract":"鑫塔水尚","price":25000,"area":"青浦","createdate":1474876845,"type":"住宅"}]
     */

    private int code;
    private String message;
    /**
     * collectid : 167
     * loupanid : 35
     * subject : 鑫塔水尚
     * thumb : userfiles/image/20151225/25164045ceb902ebcc3061_200_150.jpg
     * abstract : 鑫塔水尚
     * price : 25000
     * area : 青浦
     * createdate : 1474876845
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
        private String hetongwenben; //代销合同
        private int aid; //代销楼盘ID
        private int cid;//浏览ID
        private int collectid;
        private int loupanid;
        private String subject;
        private String thumb;
        @SerializedName("abstract")
        private String abstractX;
        private int price;
        private String area;
        private int createdate;
        private String type;
        private String hint;
        private String salestatus;
        private boolean isCheck;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getHetongwenben() {
            return hetongwenben;
        }

        public void setHetongwenben(String hetongwenben) {
            this.hetongwenben = hetongwenben;
        }

        public String getSalestatus() {
            return salestatus;
        }

        public void setSalestatus(String salestatus) {
            this.salestatus = salestatus;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public boolean getCheck(){
            return isCheck;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getCollectid() {
            return collectid;
        }

        public void setCollectid(int collectid) {
            this.collectid = collectid;
        }

        public int getLoupanid() {
            return loupanid;
        }

        public void setLoupanid(int loupanid) {
            this.loupanid = loupanid;
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

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

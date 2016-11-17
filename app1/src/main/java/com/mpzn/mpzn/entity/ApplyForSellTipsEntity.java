package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/10/31.
 */

public class ApplyForSellTipsEntity {

    /**
     * code : 200
     * message : succcess
     * data : [{"aid":3914,"subject":"虹桥天街","price":88000,"thumb":"userfiles/image/20160630/30104715a7f3b3cad86936_200_150.jpg","hetongwenben":"userfiles/file/20160318/18163231668d30c6566153.doc","type":"商铺","salestatus":"现房","area":"闵行","createdate":1458619270},{"aid":4950,"subject":"虹桥世界中心","thumb":"userfiles/image/20160706/06142645739855050f4240_200_150.jpg","price":88000,"hetongwenben":"","type":"商铺","salestatus":"期房","area":"青浦","createdate":1467786025}]
     */

    private int code;
    private String message;
    /**
     * aid : 3914
     * subject : 虹桥天街
     * price : 88000
     * thumb : userfiles/image/20160630/30104715a7f3b3cad86936_200_150.jpg
     * hetongwenben : userfiles/file/20160318/18163231668d30c6566153.doc
     * type : 商铺
     * salestatus : 现房
     * area : 闵行
     * createdate : 1458619270
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
        private int price;
        private String thumb;
        private String hetongwenben;
        private String type;
        private String salestatus;
        private String area;
        private int createdate;

        // 暂无数据属性
        private boolean isNoData = false;
        public boolean isNoData() {
            return isNoData;
        }

        public void setNoData(boolean noData) {
            isNoData = noData;
        }



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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getHetongwenben() {
            return hetongwenben;
        }

        public void setHetongwenben(String hetongwenben) {
            this.hetongwenben = hetongwenben;
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


    }
}

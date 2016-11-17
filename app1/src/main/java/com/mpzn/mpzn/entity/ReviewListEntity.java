package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/10/9.
 */
public class ReviewListEntity {

    /**
     * code : 200
     * message : success
     * data : [{"cid":1,"mname":"15921811520","createdate":1475224835,"content":"这是第一条评论","favour":10,"is_dianzan":0,"headimage":"http://mpzn99.mpzn.com/userfiles/image/20160929/57ecc51c65382.png"},{"cid":4,"mname":"15921811520","createdate":1475224835,"content":"这是第一条评论","favour":234,"is_dianzan":1,"headimage":"http://mpzn99.mpzn.com/userfiles/image/20160929/57ecc51c65382.png"},{"cid":5,"mname":"15921811520","createdate":1475224835,"content":"这是第一条评论","favour":1234,"is_dianzan":0,"headimage":"http://mpzn99.mpzn.com/userfiles/image/20160929/57ecc51c65382.png"}]
     */

    private int code;
    private String message;
    /**
     * cid : 1
     * mname : 15921811520
     * createdate : 1475224835
     * content : 这是第一条评论
     * favour : 10
     * is_dianzan : 0
     * headimage : http://mpzn99.mpzn.com/userfiles/image/20160929/57ecc51c65382.png
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
        private String mname;
        private int createdate;
        private String content;
        private int favour;
        private int is_dianzan;
        private String headimage;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public int getCreatedate() {
            return createdate;
        }

        public void setCreatedate(int createdate) {
            this.createdate = createdate;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFavour() {
            return favour;
        }

        public void setFavour(int favour) {
            this.favour = favour;
        }

        public int getIs_dianzan() {
            return is_dianzan;
        }

        public void setIs_dianzan(int is_dianzan) {
            this.is_dianzan = is_dianzan;
        }

        public String getHeadimage() {
            return headimage;
        }

        public void setHeadimage(String headimage) {
            this.headimage = headimage;
        }
    }
}

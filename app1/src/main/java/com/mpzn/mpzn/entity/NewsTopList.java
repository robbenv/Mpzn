package com.mpzn.mpzn.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Icy on 2016/9/6.
 */
public class NewsTopList {


    /**
     * code : 200
     * message : success
     * data : [{"aid":4849,"subject":"清楚了！买不买房10年后差别竟这样大","abstract":"楼市一直是人们必不可少的谈论话题之一。买不买房？这个争议始终伴随着千千万万的家庭。今天我们来看看，买房或者不买房，10年后财富差别有多大？ 第一种人：买房 假设在一个二、三线城市，房屋总价为50万的地方 ...","img":"http://mpzn99.mpzn.com/thinkphp/Public/img/aid484906132723c3aa0b43115127_400_300.jpg"},{"aid":4883,"subject":"花100万买市中心商住房 房子不难寻但未必合算","abstract":"在内环内找一套总价100万元的住宅很难，犹如大海捞针，但找一套商住物业却不难。不过，投资商住物业必须要小心，商住物业的税费、水电煤、物业管理费都不是一般的贵。 住宅限购日趋严厉，最近北京有消息称，商住 ...","img":"http://mpzn99.mpzn.com/thinkphp/Public/img/aid48831515191152b0e8d4566269_636_373.jpg"}]
     */

    private int code;
    private String message;
    /**
     * aid : 4849
     * subject : 清楚了！买不买房10年后差别竟这样大
     * abstract : 楼市一直是人们必不可少的谈论话题之一。买不买房？这个争议始终伴随着千千万万的家庭。今天我们来看看，买房或者不买房，10年后财富差别有多大？ 第一种人：买房 假设在一个二、三线城市，房屋总价为50万的地方 ...
     * img : http://mpzn99.mpzn.com/thinkphp/Public/img/aid484906132723c3aa0b43115127_400_300.jpg
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
        @SerializedName("abstract")
        private String abstractX;
        private String img;

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

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}

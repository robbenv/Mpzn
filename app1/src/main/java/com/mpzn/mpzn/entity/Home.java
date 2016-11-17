package com.mpzn.mpzn.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Icy on 2016/7/15.
 */
public class Home implements Serializable {

    /**
     * hasNext : false
     * hotsell : [{"address":"青浦徐泾高泾路358弄","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28153637dc160c95ba7420_100_80.jpg","name":"大华西郊别墅","price":60000,"type":2,"extra":"最低价1500万元/套","weifang":1},{"address":"浦东新环北路1333弄","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28155436c7eafaac223197_100_80.jpg","name":"中洲里程","price":24500,"type":2,"extra":"","weifang":1},{"address":"松江雪家桥1号","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28163716e83821a7a69953_100_80.jpg","name":"象屿品城","price":28000,"type":2,"extra":"","weifang":1}]
     * discount : [{"address":"青浦徐泾高泾路358弄","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28153637dc160c95ba7420_100_80.jpg","name":"大华西郊别墅","price":60000,"type":2,"extra":"最低价1500万元/套","weifang":1},{"address":"浦东新环北路1333弄","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28155436c7eafaac223197_100_80.jpg","name":"中洲里程","price":24500,"type":2,"extra":"","weifang":1},{"address":"松江雪家桥1号","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28163716e83821a7a69953_100_80.jpg","name":"象屿品城","price":28000,"type":2,"extra":"","weifang":1}]
     * news : [{"title":"那些陆续到期的房子该怎么办 续期收费应从低","type":"政策"},{"title":"别让房价过快上涨成为\u201c去库存\u201d拦路虎","type":"楼市"},{"title":"媒体：环京楼市涨幅远超北京 调控政策即将出台","type":"国内"},{"title":"营改增对老百姓有何影响：二手房交易仍享税收优惠","type":"金融"},{"title":"楼市火热土地市场冷淡 北京3月住宅地或零成交","type":"土地"},{"title":"年后买房第一炮 这6盘总价100万不到便可买！","type":"优惠"}]
     * recommend : [{"area":"宝山","img":"http://p0.meituan.net/165.220/movie/d196d1391e1dc1eff190275a57a60d6c261789.jpg","name":"金地天地云墅","price":34000,"type":0},{"area":"浦东","img":"http://p0.meituan.net/165.220/movie/d196d1391e1dc1eff190275a57a60d6c261789.jpg","name":"森兰自由度","price":28000,"type":1},{"area":"宝山","img":"http://p0.meituan.net/165.220/movie/d196d1391e1dc1eff190275a57a60d6c261789.jpg","name":"恒盛豪庭","price":37000,"type":1}]
     */

    private DataEntity data;
    /**
     * data : {"hasNext":false,"hotsell":[{"address":"青浦徐泾高泾路358弄","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28153637dc160c95ba7420_100_80.jpg","name":"大华西郊别墅","price":60000,"type":2,"extra":"最低价1500万元/套","weifang":1},{"address":"浦东新环北路1333弄","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28155436c7eafaac223197_100_80.jpg","name":"中洲里程","price":24500,"type":2,"extra":"","weifang":1},{"address":"松江雪家桥1号","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28163716e83821a7a69953_100_80.jpg","name":"象屿品城","price":28000,"type":2,"extra":"","weifang":1}],"discount":[{"address":"青浦徐泾高泾路358弄","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28153637dc160c95ba7420_100_80.jpg","name":"大华西郊别墅","price":60000,"type":2,"extra":"最低价1500万元/套","weifang":1},{"address":"浦东新环北路1333弄","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28155436c7eafaac223197_100_80.jpg","name":"中洲里程","price":24500,"type":2,"extra":"","weifang":1},{"address":"松江雪家桥1号","area":"青浦","biesu":1,"img":"http://www.mpzn.com/userfiles/image/20160128/28163716e83821a7a69953_100_80.jpg","name":"象屿品城","price":28000,"type":2,"extra":"","weifang":1}],"news":[{"title":"那些陆续到期的房子该怎么办 续期收费应从低","type":"政策"},{"title":"别让房价过快上涨成为\u201c去库存\u201d拦路虎","type":"楼市"},{"title":"媒体：环京楼市涨幅远超北京 调控政策即将出台","type":"国内"},{"title":"营改增对老百姓有何影响：二手房交易仍享税收优惠","type":"金融"},{"title":"楼市火热土地市场冷淡 北京3月住宅地或零成交","type":"土地"},{"title":"年后买房第一炮 这6盘总价100万不到便可买！","type":"优惠"}],"recommend":[{"area":"宝山","img":"http://p0.meituan.net/165.220/movie/d196d1391e1dc1eff190275a57a60d6c261789.jpg","name":"金地天地云墅","price":34000,"type":0},{"area":"浦东","img":"http://p0.meituan.net/165.220/movie/d196d1391e1dc1eff190275a57a60d6c261789.jpg","name":"森兰自由度","price":28000,"type":1},{"area":"宝山","img":"http://p0.meituan.net/165.220/movie/d196d1391e1dc1eff190275a57a60d6c261789.jpg","name":"恒盛豪庭","price":37000,"type":1}]}
     * status : 0
     */

    private int status;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataEntity getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static class DataEntity implements Serializable{
        private boolean hasNext;
        /**
         * address : 青浦徐泾高泾路358弄
         * area : 青浦
         * biesu : 1
         * img : http://www.mpzn.com/userfiles/image/20160128/28153637dc160c95ba7420_100_80.jpg
         * name : 大华西郊别墅
         * price : 60000
         * type : 2
         * extra : 最低价1500万元/套
         * weifang : 1
         */

        private List<HotsellEntity> hotsell;
        /**
         * address : 青浦徐泾高泾路358弄
         * area : 青浦
         * biesu : 1
         * img : http://www.mpzn.com/userfiles/image/20160128/28153637dc160c95ba7420_100_80.jpg
         * name : 大华西郊别墅
         * price : 60000
         * type : 2
         * extra : 最低价1500万元/套
         * weifang : 1
         */

        private List<DiscountEntity> discount;
        /**
         * title : 那些陆续到期的房子该怎么办 续期收费应从低
         * type : 政策
         */

        private List<NewsEntity> news;
        /**
         * area : 宝山
         * img : http://p0.meituan.net/165.220/movie/d196d1391e1dc1eff190275a57a60d6c261789.jpg
         * name : 金地天地云墅
         * price : 34000
         * type : 0
         */

        private List<RecommendEntity> recommend;

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public void setHotsell(List<HotsellEntity> hotsell) {
            this.hotsell = hotsell;
        }

        public void setDiscount(List<DiscountEntity> discount) {
            this.discount = discount;
        }

        public void setNews(List<NewsEntity> news) {
            this.news = news;
        }

        public void setRecommend(List<RecommendEntity> recommend) {
            this.recommend = recommend;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public List<HotsellEntity> getHotsell() {
            return hotsell;
        }

        public List<DiscountEntity> getDiscount() {
            return discount;
        }

        public List<NewsEntity> getNews() {
            return news;
        }

        public List<RecommendEntity> getRecommend() {
            return recommend;
        }

        public static class HotsellEntity implements Serializable{
            private String address;
            private String area;
            private int biesu;
            private String img;
            private String name;
            private int price;
            private int type;
            private String extra;
            private int weifang;

            public void setAddress(String address) {
                this.address = address;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public void setBiesu(int biesu) {
                this.biesu = biesu;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setExtra(String extra) {
                this.extra = extra;
            }

            public void setWeifang(int weifang) {
                this.weifang = weifang;
            }

            public String getAddress() {
                return address;
            }

            public String getArea() {
                return area;
            }

            public int getBiesu() {
                return biesu;
            }

            public String getImg() {
                return img;
            }

            public String getName() {
                return name;
            }

            public int getPrice() {
                return price;
            }

            public int getType() {
                return type;
            }

            public String getExtra() {
                return extra;
            }

            public int getWeifang() {
                return weifang;
            }
        }

        public static class DiscountEntity implements Serializable{
            private String address;
            private String area;
            private int biesu;
            private String img;
            private String name;
            private int price;
            private int type;
            private String extra;
            private int weifang;

            public void setAddress(String address) {
                this.address = address;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public void setBiesu(int biesu) {
                this.biesu = biesu;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setExtra(String extra) {
                this.extra = extra;
            }

            public void setWeifang(int weifang) {
                this.weifang = weifang;
            }

            public String getAddress() {
                return address;
            }

            public String getArea() {
                return area;
            }

            public int getBiesu() {
                return biesu;
            }

            public String getImg() {
                return img;
            }

            public String getName() {
                return name;
            }

            public int getPrice() {
                return price;
            }

            public int getType() {
                return type;
            }

            public String getExtra() {
                return extra;
            }

            public int getWeifang() {
                return weifang;
            }
        }

        public static class NewsEntity  implements Serializable{
            private String title;
            private String type;

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public String getType() {
                return type;
            }
        }

        public static class RecommendEntity implements Serializable {
            private String area;
            private String img;
            private String name;
            private int price;
            private int type;

            public void setArea(String area) {
                this.area = area;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getArea() {
                return area;
            }

            public String getImg() {
                return img;
            }

            public String getName() {
                return name;
            }

            public int getPrice() {
                return price;
            }

            public int getType() {
                return type;
            }
        }
    }
}

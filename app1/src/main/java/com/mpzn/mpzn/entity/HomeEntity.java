package com.mpzn.mpzn.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Icy on 2016/11/4.
 */
public class HomeEntity implements Serializable{


    /**
     * code : 200
     * message : success
     * data : {"baobeilist":[{"baobeitime":1458630690,"xingming":"王双","subject":"鑫塔水尚"},{"baobeitime":1458632903,"xingming":"王双","subject":"鑫塔水尚"},{"baobeitime":1458633457,"xingming":"蔡先生","subject":"鑫塔水尚"},{"baobeitime":1458708697,"xingming":"王双","subject":"鑫塔水尚"},{"baobeitime":1459301225,"xingming":"蔡先生","subject":"鑫塔水尚"},{"baobeitime":1459835857,"xingming":"王双","subject":"鑫塔水尚"},{"baobeitime":1460340916,"xingming":"王双","subject":"鑫塔水尚"},{"baobeitime":1463227791,"xingming":"王双","subject":"鑫塔水尚"},{"baobeitime":1463227922,"xingming":"王双","subject":"鑫塔水尚"},{"baobeitime":1463231655,"xingming":"王双","subject":"鑫塔水尚"},{"baobeitime":1463373752,"xingming":"wangshuang","subject":"沃宝天街"},{"baobeitime":1463391950,"xingming":"王双","subject":"沃宝天街"},{"baobeitime":1463392274,"xingming":"王双","subject":"沃宝天街"},{"baobeitime":1463485093,"xingming":"苏杭","subject":"雅居乐星徽"},{"baobeitime":1463576112,"xingming":"郭静鹏","subject":"安贝尔花园"},{"baobeitime":1463576346,"xingming":"郭静海","subject":"安贝尔花园"},{"baobeitime":1463576881,"xingming":"吴洵","subject":"安贝尔花园"},{"baobeitime":1463634125,"xingming":"王双","subject":"沃宝天街"},{"baobeitime":1463634148,"xingming":"王双","subject":"沃宝天街"},{"baobeitime":1463640901,"xingming":"王双","subject":"沃宝天街"}],"topnewslist":[{"aid":1814,"thumb":"http://mpzn8.mpzn.com/userfiles/image/newpic/ban2.jpg"},{"aid":4826,"thumb":"http://mpzn8.mpzn.com/userfiles/image/newpic/Center.jpg"}],"recommendlist":[{"aid":4950,"subject":"虹桥世界中心","thumb":"http://appi.mpzn.com/userfiles/image/newpic/piclist3.jpg","dj":"39000"},{"aid":4933,"subject":"上实和墅","thumb":"http://appi.mpzn.com/userfiles/image/newpic/piclist2.jpg","dj":"40000"},{"aid":4927,"subject":"海上湾闻涧","thumb":"http://appi.mpzn.com/userfiles/image/newpic/piclist1.jpg","dj":"20000"}],"news":[{"aid":4957,"subject":"全球150大城市房价涨幅榜：深圳上海稳居前两位","title":"楼市快讯"},{"aid":4956,"subject":"人社部回应\u201c工资没涨社保费涨\u201d：少数人受影响","title":"楼市焦点"},{"aid":4954,"subject":"下半年房地产市场七大预言 四季度将量价齐涨","title":"楼市快讯"},{"aid":4953,"subject":"中介老总：国内竞争格局不正常 行业都乱了","title":"楼市焦点"},{"aid":4949,"subject":"沪颁不动产\u201c第一证\u201d！你想了解的答案在这里！","title":"政策"},{"aid":4948,"subject":"百城房价半年累计涨7.61% 下半年或\u201c踩刹车\u201d","title":"楼市焦点"}]}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * baobeitime : 1458630690
         * xingming : 王双
         * subject : 鑫塔水尚
         */

        private List<BaobeilistBean> baobeilist;
        /**
         * aid : 1814
         * thumb : http://mpzn8.mpzn.com/userfiles/image/newpic/ban2.jpg
         */

        private List<TopnewslistBean> topnewslist;
        /**
         * aid : 4950
         * subject : 虹桥世界中心
         * thumb : http://appi.mpzn.com/userfiles/image/newpic/piclist3.jpg
         * dj : 39000
         */

        private List<RecommendlistBean> recommendlist;
        /**
         * aid : 4957
         * subject : 全球150大城市房价涨幅榜：深圳上海稳居前两位
         * title : 楼市快讯
         */

        private List<NewsBean> news;

        public List<BaobeilistBean> getBaobeilist() {
            return baobeilist;
        }

        public void setBaobeilist(List<BaobeilistBean> baobeilist) {
            this.baobeilist = baobeilist;
        }

        public List<TopnewslistBean> getTopnewslist() {
            return topnewslist;
        }

        public void setTopnewslist(List<TopnewslistBean> topnewslist) {
            this.topnewslist = topnewslist;
        }

        public List<RecommendlistBean> getRecommendlist() {
            return recommendlist;
        }

        public void setRecommendlist(List<RecommendlistBean> recommendlist) {
            this.recommendlist = recommendlist;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class BaobeilistBean implements Serializable{
            private int baobeitime;
            private String xingming;
            private String subject;

            public int getBaobeitime() {
                return baobeitime;
            }

            public void setBaobeitime(int baobeitime) {
                this.baobeitime = baobeitime;
            }

            public String getXingming() {
                return xingming;
            }

            public void setXingming(String xingming) {
                this.xingming = xingming;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }
        }

        public static class TopnewslistBean implements Serializable{
            private int aid;
            private String thumb;

            public int getAid() {
                return aid;
            }

            public void setAid(int aid) {
                this.aid = aid;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }
        }

        public static class RecommendlistBean implements Serializable{
            private int aid;
            private String subject;
            private String thumb;
            private String dj;

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

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getDj() {
                return dj;
            }

            public void setDj(String dj) {
                this.dj = dj;
            }
        }

        public static class NewsBean implements Serializable{
            private int aid;
            private String subject;
            private String title;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}


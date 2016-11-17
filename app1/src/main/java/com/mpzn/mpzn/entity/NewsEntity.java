package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/8/25.
 */
public class NewsEntity {


    /**
     * code : 200
     * message : success
     * data : {"detail":{"aid":"1","createdate":"1450758917","subject":"中央鼓励房地产商降价 透露哪些重要经济信号","abstract_content":"中央经济工作会议12月18日至21日在北京举行。会议认为，明年经济社会发展特别是结构性改革任务十分繁重，战略上要坚持稳中求进、把握好节奏和力度，战术上要抓住关键点，主要是抓好去产能、去库存、去杠杆、降成 ...","title":"资讯","clicks":"55","url":"/thinkphp/Public/html/1.html"},"more":[{"tj_subject":"全球150大城市房价涨幅榜：深圳上海稳居前两位","aid":"4957"},{"tj_subject":"人社部回应\u201c工资没涨社保费涨\u201d：少数人受影响","aid":"4956"},{"tj_subject":"demo","aid":"4955"}]}
     */

    private int code;
    private String message;
    /**
     * detail : {"aid":"1","createdate":"1450758917","subject":"中央鼓励房地产商降价 透露哪些重要经济信号","abstract_content":"中央经济工作会议12月18日至21日在北京举行。会议认为，明年经济社会发展特别是结构性改革任务十分繁重，战略上要坚持稳中求进、把握好节奏和力度，战术上要抓住关键点，主要是抓好去产能、去库存、去杠杆、降成 ...","title":"资讯","clicks":"55","url":"/thinkphp/Public/html/1.html"}
     * more : [{"tj_subject":"全球150大城市房价涨幅榜：深圳上海稳居前两位","aid":"4957"},{"tj_subject":"人社部回应\u201c工资没涨社保费涨\u201d：少数人受影响","aid":"4956"},{"tj_subject":"demo","aid":"4955"}]
     */

    private DataEntity data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * aid : 1
         * createdate : 1450758917
         * subject : 中央鼓励房地产商降价 透露哪些重要经济信号
         * abstract_content : 中央经济工作会议12月18日至21日在北京举行。会议认为，明年经济社会发展特别是结构性改革任务十分繁重，战略上要坚持稳中求进、把握好节奏和力度，战术上要抓住关键点，主要是抓好去产能、去库存、去杠杆、降成 ...
         * title : 资讯
         * clicks : 55
         * url : /thinkphp/Public/html/1.html
         */

        private DetailEntity detail;
        /**
         * tj_subject : 全球150大城市房价涨幅榜：深圳上海稳居前两位
         * aid : 4957
         */

        private List<MoreEntity> more;

        public void setDetail(DetailEntity detail) {
            this.detail = detail;
        }

        public void setMore(List<MoreEntity> more) {
            this.more = more;
        }

        public DetailEntity getDetail() {
            return detail;
        }

        public List<MoreEntity> getMore() {
            return more;
        }

        public static class DetailEntity {
            private String aid;
            private String createdate;
            private String subject;
            private String abstract_content;
            private String title;
            private String clicks;
            private String url;

            public void setAid(String aid) {
                this.aid = aid;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public void setAbstract_content(String abstract_content) {
                this.abstract_content = abstract_content;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setClicks(String clicks) {
                this.clicks = clicks;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getAid() {
                return aid;
            }

            public String getCreatedate() {
                return createdate;
            }

            public String getSubject() {
                return subject;
            }

            public String getAbstract_content() {
                return abstract_content;
            }

            public String getTitle() {
                return title;
            }

            public String getClicks() {
                return clicks;
            }

            public String getUrl() {
                return url;
            }
        }

        public static class MoreEntity {
            private String tj_subject;
            private String aid;

            public void setTj_subject(String tj_subject) {
                this.tj_subject = tj_subject;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getTj_subject() {
                return tj_subject;
            }

            public String getAid() {
                return aid;
            }
        }
    }
}

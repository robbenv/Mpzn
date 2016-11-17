package com.mpzn.mpzn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Icy on 2016/8/25.
 */
public class NewsList implements Parcelable {

    /**
     * code : 200
     * message : success
     * data : {"total":232,"news":[{"aid":4957,"subject":"全球150大城市房价涨幅榜：深圳上海稳居前两位","abstract":"在国际顶级置业顾问莱坊（KnightFrank）最新发布的全球大城市一季度房价涨幅排行榜上，深圳和上海分别登上冠军和亚军宝座。 莱坊综合央行们和官方统计数据计算，全球150个大型城市今年一季度的房价平均上涨了4.5 ...","createdate":1468219469,"clicks":38,"editor":"bianji_xiaohe","thumb":"userfiles/image/20160711/1114442398213d169e8432_180_120.jpg"},{"aid":4956,"subject":"人社部回应\u201c工资没涨社保费涨\u201d：少数人受影响","abstract":"新京报讯近日，各地陆续公布了2015年度社会平均工资，并以此为标准上调了5项社会保险的缴费基数。有媒体报道，许多企业的职工反映\u201c本人工资没有涨，但缴费涨了许多\u201d。对此，昨日，人社部相关负责人解释：大多 ...","createdate":1467970136,"clicks":48,"editor":"bianji_xiaohe","thumb":"userfiles/image/20160708/0817285679dbaa201f4523_180_120.jpg"}]}
     */

    private int code;
    private String message;
    /**
     * total : 232
     * news : [{"aid":4957,"subject":"全球150大城市房价涨幅榜：深圳上海稳居前两位","abstract":"在国际顶级置业顾问莱坊（KnightFrank）最新发布的全球大城市一季度房价涨幅排行榜上，深圳和上海分别登上冠军和亚军宝座。 莱坊综合央行们和官方统计数据计算，全球150个大型城市今年一季度的房价平均上涨了4.5 ...","createdate":1468219469,"clicks":38,"editor":"bianji_xiaohe","thumb":"userfiles/image/20160711/1114442398213d169e8432_180_120.jpg"},{"aid":4956,"subject":"人社部回应\u201c工资没涨社保费涨\u201d：少数人受影响","abstract":"新京报讯近日，各地陆续公布了2015年度社会平均工资，并以此为标准上调了5项社会保险的缴费基数。有媒体报道，许多企业的职工反映\u201c本人工资没有涨，但缴费涨了许多\u201d。对此，昨日，人社部相关负责人解释：大多 ...","createdate":1467970136,"clicks":48,"editor":"bianji_xiaohe","thumb":"userfiles/image/20160708/0817285679dbaa201f4523_180_120.jpg"}]
     */

    private DataBean data;

    protected NewsList(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<NewsList> CREATOR = new Creator<NewsList>() {
        @Override
        public NewsList createFromParcel(Parcel in) {
            return new NewsList(in);
        }

        @Override
        public NewsList[] newArray(int size) {
            return new NewsList[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }

    public static class DataBean implements Parcelable{
        private int total;
        /**
         * aid : 4957
         * subject : 全球150大城市房价涨幅榜：深圳上海稳居前两位
         * abstract : 在国际顶级置业顾问莱坊（KnightFrank）最新发布的全球大城市一季度房价涨幅排行榜上，深圳和上海分别登上冠军和亚军宝座。 莱坊综合央行们和官方统计数据计算，全球150个大型城市今年一季度的房价平均上涨了4.5 ...
         * createdate : 1468219469
         * clicks : 38
         * editor : bianji_xiaohe
         * thumb : userfiles/image/20160711/1114442398213d169e8432_180_120.jpg
         */

        private List<NewsBean> news;

        protected DataBean(Parcel in) {
            total = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(total);
        }

        public static class NewsBean {
            private int aid;
            private String subject;
            @SerializedName("abstract")
            private String abstractX;
            private int createdate;
            private int clicks;
            private String editor;
            private String thumb;

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

            public int getCreatedate() {
                return createdate;
            }

            public void setCreatedate(int createdate) {
                this.createdate = createdate;
            }

            public int getClicks() {
                return clicks;
            }

            public void setClicks(int clicks) {
                this.clicks = clicks;
            }

            public String getEditor() {
                return editor;
            }

            public void setEditor(String editor) {
                this.editor = editor;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }
        }
    }
}

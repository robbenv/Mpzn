package com.mpzn.mpzn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Icy on 2016/8/26.
 */
public class HomeNewsEntity implements Parcelable {

    /**
     * code : 200
     * message : success
     * data : [{"aid":4957,"subject":"全球150大城市房价涨幅榜：深圳上海稳居前两位","title":"楼市快讯"},{"aid":4956,"subject":"人社部回应\u201c工资没涨社保费涨\u201d：少数人受影响","title":"楼市焦点"},{"aid":4955,"subject":"demo","title":"政策法规"},{"aid":4954,"subject":"下半年房地产市场七大预言 四季度将量价齐涨","title":"楼市快讯"},{"aid":4953,"subject":"中介老总：国内竞争格局不正常 行业都乱了","title":"楼市焦点"},{"aid":4949,"subject":"沪颁不动产\u201c第一证\u201d！你想了解的答案在这里！","title":"政策"}]
     */

    private int code;
    private String message;
    /**
     * aid : 4957
     * subject : 全球150大城市房价涨幅榜：深圳上海稳居前两位
     * title : 楼市快讯
     */

    private List<DataEntity> data;

    protected HomeNewsEntity(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<HomeNewsEntity> CREATOR = new Creator<HomeNewsEntity>() {
        @Override
        public HomeNewsEntity createFromParcel(Parcel in) {
            return new HomeNewsEntity(in);
        }

        @Override
        public HomeNewsEntity[] newArray(int size) {
            return new HomeNewsEntity[size];
        }
    };

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<DataEntity> getData() {
        return data;
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

    public static class DataEntity implements Parcelable{
        private int aid;
        private String subject;
        private String title;

        protected DataEntity(Parcel in) {
            aid = in.readInt();
            subject = in.readString();
            title = in.readString();
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel in) {
                return new DataEntity(in);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };

        public void setAid(int aid) {
            this.aid = aid;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAid() {
            return aid;
        }

        public String getSubject() {
            return subject;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(aid);
            dest.writeString(subject);
            dest.writeString(title);
        }
    }
}

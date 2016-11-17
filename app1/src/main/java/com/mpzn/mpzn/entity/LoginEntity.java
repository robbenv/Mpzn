package com.mpzn.mpzn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Icy on 2016/9/1.
 */
public class LoginEntity implements Parcelable,Serializable{

    /**
     * code : 200
     * data : {"mchid":1,"mid":1321,"mname":"13601924972","token":"37767261676d4ed71410821b8c91a848"}
     * message : success
     */

    private int code;
    /**
     * mchid : 1
     * mid : 1321
     * mname : 13601924972
     * token : 37767261676d4ed71410821b8c91a848
     */

    private DataEntity data;
    private String message;


    protected LoginEntity(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<LoginEntity> CREATOR = new Creator<LoginEntity>() {
        @Override
        public LoginEntity createFromParcel(Parcel in) {
            return new LoginEntity(in);
        }

        @Override
        public LoginEntity[] newArray(int size) {
            return new LoginEntity[size];
        }
    };

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
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

    public static class DataEntity implements Parcelable,Serializable{
        private int mchid;
        private int mid;
        private String mname;
        private String token;
        private String hint;
        private String headimage;

        protected DataEntity(Parcel in) {
            mchid = in.readInt();
            mid = in.readInt();
            mname = in.readString();
            token = in.readString();
            hint = in.readString();
            headimage = in.readString();
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

        public void setHeadimage(String headimage) {
            this.headimage = headimage;
        }

        public String getHeadimage() {
            return headimage;
        }

        public void setMchid(int mchid) {
            this.mchid = mchid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getMchid() {
            return mchid;
        }

        public int getMid() {
            return mid;
        }

        public String getMname() {
            return mname;
        }

        public String getToken() {
            return token;
        }

        public String getHint() {
            return hint;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mchid);
            dest.writeInt(mid);
            dest.writeString(mname);
            dest.writeString(token);
            dest.writeString(hint);
            dest.writeString(headimage);
        }
    }
}

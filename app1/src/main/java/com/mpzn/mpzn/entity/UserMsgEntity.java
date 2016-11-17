package com.mpzn.mpzn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Icy on 2016/9/23.
 */

public class UserMsgEntity implements Serializable ,Parcelable{

    /**
     * code : 200
     * message : success
     * data : {"name":"经纪人","phone":"13601924972","email":"admin@qq.com","companyName":"上海万凯投资管理有限公司"}
     */

    private int code;
    private String message;
    /**
     * name : 经纪人
     * phone : 13601924972
     * email : admin@qq.com
     * companyName : 上海万凯投资管理有限公司
     */

    private DataBean data;

    protected UserMsgEntity(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<UserMsgEntity> CREATOR = new Creator<UserMsgEntity>() {
        @Override
        public UserMsgEntity createFromParcel(Parcel in) {
            return new UserMsgEntity(in);
        }

        @Override
        public UserMsgEntity[] newArray(int size) {
            return new UserMsgEntity[size];
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

    public static class DataBean implements Serializable ,Parcelable{
        private String name;
        private String phone;
        private String email;
        private String companyName;
        private String hint;

        protected DataBean(Parcel in) {
            name = in.readString();
            phone = in.readString();
            email = in.readString();
            companyName = in.readString();
            hint = in.readString();
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

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(phone);
            dest.writeString(email);
            dest.writeString(companyName);
            dest.writeString(hint);
        }
    }
}

package com.mpzn.mpzn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Icy on 2016/9/2.
 */
public class  UserMsg implements Parcelable,Serializable {
    private int mId;
    private String mName;//账户名称
    private String name;//用户真实姓名
    private String nickName;
    private int mChild;    //账号类型
    private String mIconUrl;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static Creator<UserMsg> getCREATOR() {
        return CREATOR;
    }

    public UserMsg(int mId, String mIconUrl, int mChild, String mName, String phone ,String name, String nickName) {
        this.mId = mId;
        this.mIconUrl = mIconUrl;
        this.mChild = mChild;
        this.mName = mName;
        this.phone = phone;
        this.name = name;
        this.nickName = nickName;
    }

    protected UserMsg(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mChild = in.readInt();
        mIconUrl = in.readString();
        phone = in.readString();
        name = in.readString();
        nickName = in.readString();
    }

    public static final Creator<UserMsg> CREATOR = new Creator<UserMsg>() {
        @Override
        public UserMsg createFromParcel(Parcel in) {
            return new UserMsg(in);
        }

        @Override
        public UserMsg[] newArray(int size) {
            return new UserMsg[size];
        }
    };

    public UserMsg() {

    }

    public String getmIconUrl() {
        return mIconUrl;
    }

    public void setmIconUrl(String mIconUrl) {
        this.mIconUrl = mIconUrl;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmChild() {
        return mChild;
    }

    public void setmChild(int mChild) {
        this.mChild = mChild;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mChild);
        dest.writeString(mIconUrl);
        dest.writeString(phone);
        dest.writeString(name);
        dest.writeString(nickName);
    }
}

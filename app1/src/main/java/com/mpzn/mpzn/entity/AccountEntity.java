package com.mpzn.mpzn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Icy on 2016/9/26.
 */

public class AccountEntity implements Serializable,Parcelable {
    private String username;
    private String password;
    private String iconurl;


    public AccountEntity(String username, String password, String iconurl) {
        this.username = username;
        this.iconurl = iconurl;
        this.password = password;
    }

    public AccountEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected AccountEntity(Parcel in) {
        username = in.readString();
        password = in.readString();
        iconurl = in.readString();
    }

    public static final Creator<AccountEntity> CREATOR = new Creator<AccountEntity>() {
        @Override
        public AccountEntity createFromParcel(Parcel in) {
            return new AccountEntity(in);
        }

        @Override
        public AccountEntity[] newArray(int size) {
            return new AccountEntity[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(iconurl);
    }
}

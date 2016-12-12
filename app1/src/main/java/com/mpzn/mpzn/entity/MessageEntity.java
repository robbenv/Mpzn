package com.mpzn.mpzn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Icy on 2016/11/3.
 */

public class MessageEntity implements Serializable,Parcelable{

    @Override
    public String toString() {
        return "MessageEntity{" +
                "abstractX='" + abstractX + '\'' +
                ", aid=" + aid +
                ", subject='" + subject + '\'' +
                ", thumb='" + thumb + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", creatDate=" + creatDate +
                '}';
    }

    public MessageEntity() {
    }

    /**
     * abstract : 关于产权证上到底写谁的名字主要有5种做法，不同的方案可能导致不同的法律后果。恋爱中的男女应当对此有清晰的认识，然后再根据自己家庭和对方的实际情况选择适合的方案，以免仓促决定后，家庭、恋人反目，产生 ...
     * aid : 134
     * subject : 房产证名字怎么写？5种选择大不同
     * thumb : http://appi.mpzn.com/userfiles/image/icon.png
     * url : http://appi.mpzn.com/index.php/news/newsdetail/aid/134
     */



    @SerializedName("abstract")
    private String abstractX;
    private int aid;
    private String subject;
    private String thumb;
    private String url;
    private String type;
    private long creatDate;

    protected MessageEntity(Parcel in) {
        abstractX = in.readString();
        aid = in.readInt();
        subject = in.readString();
        thumb = in.readString();
        url = in.readString();
        type = in.readString();
        creatDate = in.readLong();
    }

    public static final Creator<MessageEntity> CREATOR = new Creator<MessageEntity>() {
        @Override
        public MessageEntity createFromParcel(Parcel in) {
            return new MessageEntity(in);
        }

        @Override
        public MessageEntity[] newArray(int size) {
            return new MessageEntity[size];
        }
    };

    public long getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(long creatDate) {
        this.creatDate = creatDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(abstractX);
        dest.writeInt(aid);
        dest.writeString(subject);
        dest.writeString(thumb);
        dest.writeString(url);
        dest.writeString(type);
        dest.writeLong(creatDate);
    }
}

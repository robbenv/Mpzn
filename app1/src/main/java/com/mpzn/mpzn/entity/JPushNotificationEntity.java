package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/11/2.
 */

public class JPushNotificationEntity {

    /**
     * id : 3914
     * type : building
     * url: www.mpzn.com
     */

    private String id;
    private String type;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JPushNotificationEntity{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

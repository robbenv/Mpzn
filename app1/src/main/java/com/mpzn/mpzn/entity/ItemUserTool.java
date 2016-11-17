package com.mpzn.mpzn.entity;

import java.io.Serializable;

/**
 * Created by Icy on 2016/8/10.
 */
public class ItemUserTool implements Serializable {
    private String name;
    private int imgResId;
    private int id;

    public ItemUserTool(String name, int imgResId ,int id) {
        this.name = name;
        this.imgResId = imgResId;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgResId() {
        return imgResId;

    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ItemUserTool{" +
                "name='" + name + '\'' +
                ", imgResId=" + imgResId +
                ", id=" + id +
                '}';
    }
}



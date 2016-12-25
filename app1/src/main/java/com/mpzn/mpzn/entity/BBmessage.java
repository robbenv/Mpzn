package com.mpzn.mpzn.entity;

/**
 * Created by larry on 16-12-8.
 */
public class BBmessage {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BBmessage{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

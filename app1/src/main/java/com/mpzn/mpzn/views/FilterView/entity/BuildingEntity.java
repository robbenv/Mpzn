package com.mpzn.mpzn.views.FilterView.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Icy on 2016/9/7.
 */
public class BuildingEntity implements Serializable, Comparable<BuildingEntity>{
        private String diqu;
        private String title;
        private int jdjj;
        private int ccid;
        private String subject;
        private int aid;
        private String thumb;
        private int dj;
        private String tslp;
        private int zxcd;
        private int clicks;
        private String address;
        private String url;

    // 暂无数据属性
    private boolean isNoData = false;
    private int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isNoData() {
        return isNoData;
    }

    public void setNoData(boolean noData) {
        isNoData = noData;
    }
        /**
         * zxcdarray : 全装修
         * tslparray : ["小户型投资","宜居生态"]
         * wylxarray : 住宅
         */

        private TagEntity tag;

        public void setDiqu(String diqu) {
            this.diqu = diqu;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setJdjj(int jdjj) {
            this.jdjj = jdjj;
        }

        public void setCcid(int ccid) {
            this.ccid = ccid;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public void setDj(int dj) {
            this.dj = dj;
        }

        public void setTslp(String tslp) {
            this.tslp = tslp;
        }

        public void setZxcd(int zxcd) {
            this.zxcd = zxcd;
        }

        public void setClicks(int clicks) {
            this.clicks = clicks;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setTag(TagEntity tag) {
            this.tag = tag;
        }

        public String getDiqu() {
            return diqu;
        }

        public String getTitle() {
            return title;
        }

        public int getJdjj() {
            return jdjj;
        }

        public int getCcid() {
            return ccid;
        }

        public String getSubject() {
            return subject;
        }

        public int getAid() {
            return aid;
        }

        public String getThumb() {
            return thumb;
        }

        public int getDj() {
            return dj;
        }

        public String getTslp() {
            return tslp;
        }

        public int getZxcd() {
            return zxcd;
        }

        public int getClicks() {
            return clicks;
        }

        public String getAddress() {
            return address;
        }

        public String getUrl() {
            return url;
        }

        public TagEntity getTag() {
            return tag;
        }

    @Override
    public int compareTo(BuildingEntity another) {
        return this.getDj() - another.getDj();
    }

    public static class TagEntity implements Serializable{
            private String zxcdarray;
            private String wylxarray;
            private List<String> tslparray;

            public void setZxcdarray(String zxcdarray) {
                this.zxcdarray = zxcdarray;
            }

            public void setWylxarray(String wylxarray) {
                this.wylxarray = wylxarray;
            }

            public void setTslparray(List<String> tslparray) {
                this.tslparray = tslparray;
            }

            public String getZxcdarray() {
                return zxcdarray;
            }

            public String getWylxarray() {
                return wylxarray;
            }

            public List<String> getTslparray() {
                return tslparray;
            }
        }

}

package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/10/13.
 */

public class JingjirenListEntity {

    /**
     * code : 200
     * message : succcess
     * data : [{"mid":1294,"mname":"18217551993","xingming":"韦宁","lastvisit":1467624782,"headimage":"","checked":1},{"mid":1295,"mname":"15026743191","xingming":"章五霖","lastvisit":1467624789,"headimage":"","checked":1}]
     */

    private int code;
    private String message;
    /**
     * mid : 1294
     * mname : 18217551993
     * xingming : 韦宁
     * lastvisit : 1467624782
     * headimage :
     * checked : 1
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private boolean check;
        private int mid;
        private String mname;
        private String xingming;
        private int lastvisit;
        private String headimage;
        private int checked;

        public void setCheck(boolean check) {
            this.check = check;
        }

        public boolean getCheck(){
            return check;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getXingming() {
            return xingming;
        }

        public void setXingming(String xingming) {
            this.xingming = xingming;
        }

        public int getLastvisit() {
            return lastvisit;
        }

        public void setLastvisit(int lastvisit) {
            this.lastvisit = lastvisit;
        }

        public String getHeadimage() {
            return headimage;
        }

        public void setHeadimage(String headimage) {
            this.headimage = headimage;
        }

        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }
    }
}

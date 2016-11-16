package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/9/9.
 */
public class BuildingTJEntity {


    /**
     * code : 200
     * data : [{"dj":"39000","fromid":4950,"subject":"虹桥世界中心","thumb":"http://mpzn8.mpzn.com/userfiles/image/20151225/25164045ceb902ebcc3061.jpg#770#578","title":"青浦"},{"dj":"40000","fromid":4933,"subject":"上实和墅","thumb":"http://mpzn8.mpzn.com/userfiles/image/20151225/25164045ceb902ebcc3061.jpg#770#578","title":"青浦"},{"dj":"20000","fromid":4927,"subject":"海上湾闻涧","thumb":"http://mpzn8.mpzn.com/userfiles/image/20151225/25164045ceb902ebcc3061.jpg#770#578","title":"青浦"}]
     * message : success
     */

    private int code;
    private String message;
    /**
     * dj : 39000
     * fromid : 4950
     * subject : 虹桥世界中心
     * thumb : http://mpzn8.mpzn.com/userfiles/image/20151225/25164045ceb902ebcc3061.jpg#770#578
     * title : 青浦
     */

    private List<DataEntity> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String dj;
        private int fromid;
        private String subject;
        private String thumb;
        private String title;

        public void setDj(String dj) {
            this.dj = dj;
        }

        public void setFromid(int fromid) {
            this.fromid = fromid;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDj() {
            return dj;
        }

        public int getFromid() {
            return fromid;
        }

        public String getSubject() {
            return subject;
        }

        public String getThumb() {
            return thumb;
        }

        public String getTitle() {
            return title;
        }
    }
}

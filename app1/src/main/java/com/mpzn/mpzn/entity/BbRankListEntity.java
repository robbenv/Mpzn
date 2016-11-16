package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/11/1.
 */

public class BbRankListEntity {


    /**
     * code : 200
     * message : success
     * data : {"agent":{"mid":1231,"xingming":"经纪公司","image":"http://mpzn99.mpzn.com/userfiles/image/20161026/5810159b05b3a.png","success":9},"brokersbaobei":[{"mid":1288,"xingming":"小壹","success":"13","image":"http://mpzn99.mpzn.com/userfiles/image/20161026/5810155c2808b.png"},{"mid":20,"xingming":"王双","success":"3","image":"http://mpzn99.mpzn.com/userfiles/image/20161012/57fde287e1622.png"}]}
     */

    private int code;
    private String message;
    /**
     * agent : {"mid":1231,"xingming":"经纪公司","image":"http://mpzn99.mpzn.com/userfiles/image/20161026/5810159b05b3a.png","success":9}
     * brokersbaobei : [{"mid":1288,"xingming":"小壹","success":"13","image":"http://mpzn99.mpzn.com/userfiles/image/20161026/5810155c2808b.png"},{"mid":20,"xingming":"王双","success":"3","image":"http://mpzn99.mpzn.com/userfiles/image/20161012/57fde287e1622.png"}]
     */

    private DataBean data;

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

    public static class DataBean {
        /**
         * mid : 1231
         * xingming : 经纪公司
         * image : http://mpzn99.mpzn.com/userfiles/image/20161026/5810159b05b3a.png
         * success : 9
         */

        private AgentBean agent;
        /**
         * mid : 1288
         * xingming : 小壹
         * success : 13
         * image : http://mpzn99.mpzn.com/userfiles/image/20161026/5810155c2808b.png
         */

        private List<BrokersbaobeiBean> brokersbaobei;

        public AgentBean getAgent() {
            return agent;
        }

        public void setAgent(AgentBean agent) {
            this.agent = agent;
        }

        public List<BrokersbaobeiBean> getBrokersbaobei() {
            return brokersbaobei;
        }

        public void setBrokersbaobei(List<BrokersbaobeiBean> brokersbaobei) {
            this.brokersbaobei = brokersbaobei;
        }

        public static class AgentBean {
            private int mid;
            private String xingming;
            private String image;
            private int success;

            public int getMid() {
                return mid;
            }

            public void setMid(int mid) {
                this.mid = mid;
            }

            public String getXingming() {
                return xingming;
            }

            public void setXingming(String xingming) {
                this.xingming = xingming;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getSuccess() {
                return success;
            }

            public void setSuccess(int success) {
                this.success = success;
            }
        }

        public static class BrokersbaobeiBean {
            private int mid;
            private String xingming;
            private int success;
            private String image;

            public BrokersbaobeiBean(int mid, String xingming, int success, String image) {
                this.mid = mid;
                this.xingming = xingming;
                this.success = success;
                this.image = image;
            }

            public int getMid() {
                return mid;
            }

            public void setMid(int mid) {
                this.mid = mid;
            }

            public String getXingming() {
                return xingming;
            }

            public void setXingming(String xingming) {
                this.xingming = xingming;
            }

            public int getSuccess() {
                return success;
            }

            public void setSuccess(int success) {
                this.success = success;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}

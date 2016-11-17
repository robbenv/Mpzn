package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/11/14.
 */
public class BBstaticForKfsListEntity {

    /**
     * code : 200
     * message : success
     * data : {"agentbaobei":[{"company_name":"","total":95,"success":1},{"company_name":"1","total":1,"success":0},{"company_name":"121212","total":1,"success":0},{"company_name":"123123","total":1,"success":0},{"company_name":"99","total":1,"success":0},{"company_name":"qqq","total":1,"success":0},{"company_name":"xixixiixi","total":1,"success":0},{"company_name":"万凯","total":9,"success":0},{"company_name":"万凯投资","total":668,"success":1},{"company_name":"上海XX地产有限公司","total":1,"success":0},{"company_name":"上海XX地产经纪有限公司","total":1,"success":0},{"company_name":"上海万侯网络科技有限公司","total":3,"success":1},{"company_name":"上海万候网络科技有限公司","total":2,"success":1},{"company_name":"上海万凯投资管理有限公司","total":16,"success":1},{"company_name":"上海中原物业顾问有效公司","total":1,"success":0},{"company_name":"上海中原物业顾问有限公司","total":8,"success":0},{"company_name":"上海友情岁月投资管理有限公司","total":2,"success":0},{"company_name":"上海壹宝电子商务有限公司","total":1,"success":0},{"company_name":"上海奔跑投资管理有限公司","total":1,"success":0},{"company_name":"上海巨众投资管理有限公司","total":6,"success":1},{"company_name":"上海曦越","total":1,"success":0},{"company_name":"上海立家房地产经纪事务所","total":3,"success":1},{"company_name":"上海聚硬投资有限公司","total":2,"success":1},{"company_name":"上海鎏翔投资管理有限公司","total":7,"success":0},{"company_name":"上海骏凌投资有限公司","total":1,"success":0},{"company_name":"中原地产","total":10,"success":0},{"company_name":"中原地产工商铺","total":1,"success":0},{"company_name":"供货商","total":2,"success":0},{"company_name":"公司","total":1,"success":0},{"company_name":"卖盘指南","total":1,"success":0},{"company_name":"哈哈哈","total":1,"success":0},{"company_name":"嘻嘻嘻","total":1,"success":0},{"company_name":"好的呀","total":1,"success":0},{"company_name":"宝原9","total":1,"success":0},{"company_name":"客户","total":1,"success":0},{"company_name":"很哇塞","total":1,"success":0},{"company_name":"志盈投资管理有限公司","total":1,"success":0},{"company_name":"恒居地产","total":2,"success":1},{"company_name":"我的","total":1,"success":0},{"company_name":"远行佳地产","total":2,"success":1},{"company_name":"鎏翔","total":3,"success":0},{"company_name":"鎏翔投资","total":67,"success":0},{"company_name":"鎏翔投资渠道","total":8,"success":0},{"company_name":"链家","total":8,"success":0},{"company_name":"锦圆实业","total":1,"success":0}]}
     */

    private int code;
    private String message;
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
        private List<AgentbaobeiBean> agentbaobei;

        public List<AgentbaobeiBean> getAgentbaobei() {
            return agentbaobei;
        }

        public void setAgentbaobei(List<AgentbaobeiBean> agentbaobei) {
            this.agentbaobei = agentbaobei;
        }

        public static class AgentbaobeiBean {
            /**
             * company_name :
             * total : 95
             * success : 1
             */

            private String company_name;
            private int total;
            private int success;

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getSuccess() {
                return success;
            }

            public void setSuccess(int success) {
                this.success = success;
            }
        }
    }
}

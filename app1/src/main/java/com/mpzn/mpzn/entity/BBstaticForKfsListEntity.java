package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/11/14.
 */
public class BBstaticForKfsListEntity {


    /**
     * code : 200
     * message : success
     * data : {"agentbaobei":[{"company_name":"上海中原物业顾问有限公司","count":8,"agent_id":4671,"head_image":""},{"company_name":"三千石地产","count":2,"agent_id":3846,"head_image":""}]}
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
             * company_name : 上海中原物业顾问有限公司
             * count : 8
             * agent_id : 4671
             * head_image :
             */

            private String company_name;
            private int count;
            private int agent_id;
            private String head_image;

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getAgent_id() {
                return agent_id;
            }

            public void setAgent_id(int agent_id) {
                this.agent_id = agent_id;
            }

            public String getHead_image() {
                return head_image;
            }

            public void setHead_image(String head_image) {
                this.head_image = head_image;
            }
        }
    }
}

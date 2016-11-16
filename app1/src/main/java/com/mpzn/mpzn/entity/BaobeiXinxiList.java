package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by Icy on 2016/9/5.
 */
public class BaobeiXinxiList {


    /**
     * code : 200
     * data : ["15:11:30王双对楼盘鑫塔水尚报备成功！","15:48:23王双对楼盘鑫塔水尚报备成功！","12:51:37王双对楼盘鑫塔水尚报备成功！","09:27:05蔡先生对楼盘鑫塔水尚报备成功！","13:57:37王双对楼盘鑫塔水尚报备成功！","10:15:16王双对楼盘鑫塔水尚报备成功！","20:09:51王双对楼盘鑫塔水尚报备成功！","20:12:02王双对楼盘鑫塔水尚报备成功！","21:14:15王双对楼盘鑫塔水尚报备成功！","12:42:32wangshuang对楼盘沃宝天街报备成功！","19:38:13苏杭对楼盘雅居乐星徽报备成功！","20:55:12郭静鹏对楼盘安贝尔花园报备成功！","20:59:06郭静海对楼盘安贝尔花园报备成功！","21:08:01吴洵对楼盘安贝尔花园报备成功！","13:02:05王双对楼盘沃宝天街报备成功！","13:02:28王双对楼盘沃宝天街报备成功！","14:55:01王双对楼盘沃宝天街报备成功！","15:01:01王双对楼盘沃宝天街报备成功！","19:01:40王双对楼盘沃宝天街报备成功！","20:31:48吴洵对楼盘安贝尔花园 商铺报备成功！"]
     * message : success
     */

    private int code;
    private String message;
    private List<String> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getData() {
        return data;
    }
}

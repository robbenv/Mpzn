package com.mpzn.mpzn.entity;

import java.util.List;

/**
 * Created by larry on 16-12-19.
 */

public class BblistEntity {

    /**
     * code : 200
     * message : success
     * data : ["王双于08:00:00对楼盘报备成功！","王双于15:11:30对楼盘鑫塔水尚报备成功！","王双于15:48:23对楼盘鑫塔水尚报备成功！","王双于12:51:37对楼盘鑫塔水尚报备成功！","蔡先生于09:27:05对楼盘鑫塔水尚报备成功！","王双于13:57:37对楼盘鑫塔水尚报备成功！","王双于10:15:16对楼盘鑫塔水尚报备成功！","王双于20:09:51对楼盘鑫塔水尚报备成功！","王双于20:12:02对楼盘鑫塔水尚报备成功！","王双于21:14:15对楼盘鑫塔水尚报备成功！","1pm5123212000000pm512于12:42:32对楼盘沃宝天街报备成功！","苏杭于19:38:13对楼盘雅居乐星徽报备成功！","郭静鹏于20:55:12对楼盘安贝尔花园报备成功！","郭静海于20:59:06对楼盘安贝尔花园报备成功！","吴洵于21:08:01对楼盘安贝尔花园报备成功！","王双于13:02:05对楼盘沃宝天街报备成功！","王双于13:02:28对楼盘沃宝天街报备成功！","王双于14:55:01对楼盘沃宝天街报备成功！","王双于15:01:01对楼盘沃宝天街报备成功！","王双于19:01:40对楼盘沃宝天街报备成功！"]
     */

    private int code;
    private String message;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}

package com.mpzn.mpzn.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Icy on 2016/11/3.
 */
public class AbstractEntity {

    /**
     * code : 200
     * message : success
     * data : {"abstract":"关于产权证上到底写谁的名字主要有5种做法，不同的方案可能导致不同的法律后果。恋爱中的男女应当对此有清晰的认识，然后再根据自己家庭和对方的实际情况选择适合的方案，以免仓促决定后，家庭、恋人反目，产生 ...","aid":134,"subject":"房产证名字怎么写？5种选择大不同","thumb":"http://appi.mpzn.com/userfiles/image/icon.png","url":"http://appi.mpzn.com/index.php/news/newsdetail/aid/134"}
     */

    private int code;
    private String message;
    /**
     * abstract : 关于产权证上到底写谁的名字主要有5种做法，不同的方案可能导致不同的法律后果。恋爱中的男女应当对此有清晰的认识，然后再根据自己家庭和对方的实际情况选择适合的方案，以免仓促决定后，家庭、恋人反目，产生 ...
     * aid : 134
     * subject : 房产证名字怎么写？5种选择大不同
     * thumb : http://appi.mpzn.com/userfiles/image/icon.png
     * url : http://appi.mpzn.com/index.php/news/newsdetail/aid/134
     */

    private MessageEntity data;

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

    public MessageEntity getData() {
        return data;
    }

    public void setData(MessageEntity data) {
        this.data = data;
    }


}

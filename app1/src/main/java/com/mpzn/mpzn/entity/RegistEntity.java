package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/9/9.
 */
public class RegistEntity {


    /**
     * code : 200
     * message : success
     * pass : 888888
     * user : 13080736507
     */

    private int code;
    private String message;
    private String pass;
    private String user;
    private String token;


    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPass() {
        return pass;
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

}

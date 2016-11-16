package com.mpzn.mpzn.entity;

/**
 * Created by Icy on 2016/10/25.
 */

public class LoginEvent {
    private String username;
    private String pass;

    public LoginEvent(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }
}

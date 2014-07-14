package com.tsykul.joggingtracker.model;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
public class SecurityToken {
    private String token;

    public SecurityToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package com.example.authmodule.entities;

/**
 * @author Hongyu Zhang
 * @date 2022/9/18 18:06
 */
public class TokenUser {

    private String userName;

    /**
     * not encrypted
     */
    private String authToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

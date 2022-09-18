package com.example.authmodule.entities;

import java.util.Date;

/**
 * @author Hongyu Zhang
 * @date 2022/9/18 00:48
 */
public class UserToken {

    /**
     * encrypted
     */
    private String authToken;

    /**
     * userName
     */
    private String userName;

    /**
     * token created time
     */
    private Date createdTime;

    /**
     * token expired time
     */
    private Date expiredTime;

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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
}

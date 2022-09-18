package com.example.authmodule.model;

import jakarta.ws.rs.FormParam;

import java.io.Serializable;

/**
 * @author Hongyu Zhang
 * @date 2022/9/18 12:26
 */
public class UserModel implements Serializable {

    private static final long serialVersionUID = 8642188670931890735L;

    @FormParam("userName")
    private String userName;

    @FormParam("password")
    private String password;

    public UserModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

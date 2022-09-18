package com.example.authmodule.entities;

import java.util.List;

/**
 * @author Hongyu Zhang
 * @date 2022/9/17 23:33
 */
public class Users {

    /**
     * User Name
     */
    private String userName;

    /**
     * Role sets
     */
    private List<String> roleNames;

    /**
     * encryptedPassword;
     */
    private String encryptedPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}

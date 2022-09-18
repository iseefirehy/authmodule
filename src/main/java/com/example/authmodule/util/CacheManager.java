package com.example.authmodule.util;

import com.example.authmodule.entities.Roles;
import com.example.authmodule.entities.TokenUser;
import com.example.authmodule.entities.UserToken;
import com.example.authmodule.entities.Users;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hongyu Zhang
 * @date 2022/9/18 15:24
 */


public class CacheManager {

    /**
     * for user session
     * key: encrypted token;
     * value: contains encrypted token
     */
    public static Map<String, UserToken> sessionMap;
    /**
     * for users info
     * key:userName
     */
    public static Map<String, Users> usersInfoMap;
    /**
     * for roles detail
     * key:roleName
     */
    public static Map<String, Roles> rolesMap;

    /**
     * key: userName
     * value: unencrypted token
     */

    public static Map<String, TokenUser> tokenUserMap;
    private CacheManager(){};

    static {
        sessionMap = new HashMap<>();
        usersInfoMap = new HashMap<>();
        rolesMap = new HashMap<>();
        tokenUserMap = new HashMap<>();
    }

    @PostConstruct
    public void initTheCache(){
        CacheManager cacheManager = new CacheManager();
    }


}

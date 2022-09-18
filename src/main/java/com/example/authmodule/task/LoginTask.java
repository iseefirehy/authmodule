package com.example.authmodule.task;

import com.example.authmodule.entities.Roles;
import com.example.authmodule.entities.UserToken;
import com.example.authmodule.entities.Users;
import com.example.authmodule.exception.GeneralException;
import com.example.authmodule.util.CacheManager;
import com.example.authmodule.util.EncryptionUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hongyu Zhang
 * @date 2022/9/18 10:51
 */

public class LoginTask {

    public String createRole(Roles role) throws GeneralException {
        if (null == role || null == role.getRoleName()) {
            throw new GeneralException("The role input is invalid");
        }
        if (CacheManager.rolesMap.containsKey(role.getRoleName())) {
            throw new GeneralException("The role already exists");
        }
        CacheManager.rolesMap.put(role.getRoleName(), role);
        return role.toString();
    }

    public String deleteRole(Roles role) throws GeneralException {
        if (null == role || null == role.getRoleName()) {
            throw new GeneralException("The role input is invalid");
        }
        if (!CacheManager.rolesMap.containsKey(role.getRoleName())) {
            throw new GeneralException("The role doesn't exit");
        }
        CacheManager.rolesMap.remove(role.getRoleName());
        return role.getRoleName();
    }

    public void addRoleToUser(Users users, String roleName) throws GeneralException {
        if (null == users || null == users.getUserName() || null == roleName){
            throw new GeneralException("User or Roles input invalid");
        }
        if (!CacheManager.usersInfoMap.containsKey(users.getUserName()) || !CacheManager.rolesMap.containsKey(roleName)){
            throw new GeneralException("User or Roles doesn't exist");
        }
        List<String> rolesNames = CacheManager.usersInfoMap.get(users.getUserName()).getRoleNames();
        if (null == rolesNames || rolesNames.size() == 0) {
            rolesNames = new ArrayList<>();
        }
        if (rolesNames.contains(roleName)) {
            throw new GeneralException("Duplicate Roles Assign");
        }
        rolesNames.add(roleName);
        Users userUpdated = CacheManager.usersInfoMap.get(users.getUserName());
        userUpdated.setRoleNames(rolesNames);
        CacheManager.usersInfoMap.put(users.getUserName(), userUpdated);
    }

    public String checkRole(String authToken, Roles role) throws GeneralException {
        if(null == authToken || null == role) {
            throw new GeneralException("User Input is empty");
        }
        String encryptedToken = EncryptionUtil.encryptedWithRotatingHash(authToken);
        if (!CacheManager.sessionMap.containsKey(encryptedToken)){
            throw new GeneralException("Token is invalid");
        }
        UserToken userToken = CacheManager.sessionMap.get(encryptedToken);
        String userName = userToken.getUserName();
        if (!CacheManager.usersInfoMap.containsKey(userName)) {
            throw new GeneralException("User not exist");
        }
        Users user = CacheManager.usersInfoMap.get(userName);
        List<String> roles = user.getRoleNames();
        if (roles.contains(role.getRoleName())){
            return Boolean.TRUE.toString();
        } else {
            return Boolean.FALSE.toString();
        }
    }

    public List<String> allRoles(String authToken) throws GeneralException {
        if (null == authToken) {
            throw new GeneralException("Token is empty");
        }
        String encryptedToken = EncryptionUtil.encryptedWithRotatingHash(authToken);
        // If the token is already expired
        if (!CacheManager.sessionMap.containsKey(encryptedToken)) {
            throw new GeneralException("Token invalid");
        }
        // Get the user's token
        UserToken userToken = CacheManager.sessionMap.get(encryptedToken);
        // Check if the token is same
        if (!encryptedToken.equals(userToken.getAuthToken())) {
            throw new GeneralException("Token invalid");
        }
        // Check if the token is expired
        // if it is not expired, return the roles for the auth token otherwise remove this session
        Date expiredTime = userToken.getExpiredTime();
        if (expiredTime.before(new Date())){
            // expired
            CacheManager.sessionMap.remove(encryptedToken);
            throw new GeneralException("Token Expired");
        } else {
            Users user = CacheManager.usersInfoMap.get(userToken.getUserName());
            if (null != user){
                return user.getRoleNames();
            } else {
                throw new GeneralException("User Not Found");
            }
        }
    }
}

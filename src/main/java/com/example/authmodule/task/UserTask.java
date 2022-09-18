package com.example.authmodule.task;

import com.example.authmodule.entities.TokenUser;
import com.example.authmodule.entities.UserToken;
import com.example.authmodule.entities.Users;
import com.example.authmodule.exception.GeneralException;
import com.example.authmodule.model.UserModel;
import com.example.authmodule.util.CacheManager;
import com.example.authmodule.util.DateUtil;
import com.example.authmodule.util.EncryptionUtil;

import java.util.Date;
import java.util.UUID;


/**
 * @author Hongyu Zhang
 * @date 2022/9/18 10:50
 */
public class UserTask {

    public Users createUserModel(UserModel userModel){
        Users user = new Users();
        user.setUserName(userModel.getUserName());
        user.setEncryptedPassword(EncryptionUtil.encryptedWithRotatingHash(userModel.getPassword()));
        return user;
    }

    public String createUser(UserModel userModel) throws GeneralException {
        if (null == userModel || null == userModel.getUserName() || null == userModel.getPassword()) {
            throw new GeneralException("The user input is null");
        }
        if (CacheManager.usersInfoMap.containsKey(userModel.getUserName())){
            throw new GeneralException("The user already exists in system");
        }
        Users user = createUserModel(userModel);
        CacheManager.usersInfoMap.put(user.getUserName(),user);
        return user.getUserName();
    }

    public String deleteUser(Users user) throws GeneralException {
        if (null == user || null == user.getUserName()) {
            throw new GeneralException("The user input is invalid");
        }
        if (!CacheManager.usersInfoMap.containsKey(user.getUserName())) {
            throw new GeneralException("The user doesn't exit");
        }
        CacheManager.usersInfoMap.remove(user.getUserName());
        return user.getUserName();
    }

    public void invalidateSession(String authToken) throws GeneralException {
        if (null == authToken) {
            throw new GeneralException("The authToken in Empty");
        }
        String encryptedToken = EncryptionUtil.encryptedWithRotatingHash(authToken);
        if (!CacheManager.sessionMap.containsKey(encryptedToken)) {
            throw new GeneralException("Token invalid");
        }
        UserToken userToken = CacheManager.sessionMap.get(encryptedToken);
        if (!userToken.getAuthToken().equals(encryptedToken)){
            throw new GeneralException("Token invalid");
        }
        CacheManager.sessionMap.remove(encryptedToken);
        if (CacheManager.tokenUserMap.containsKey(userToken.getUserName())){
            CacheManager.tokenUserMap.remove(userToken.getUserName());
        }
    }

    public String authenticateUser(UserModel userModel) throws GeneralException {
        if (null == userModel || null == userModel.getUserName() || null == userModel.getPassword()){
            throw new GeneralException("User input wrong");
        }
        String userName = userModel.getUserName();
        String password = userModel.getPassword();
        if (!CacheManager.usersInfoMap.containsKey(userName)){
            throw new GeneralException("User not exist");
        }
        if (!CacheManager.usersInfoMap.get(userName).getEncryptedPassword().equals(EncryptionUtil.encryptedWithRotatingHash(password))){
            throw new GeneralException("Password not matched");
        }
        // random token
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        // match the username + the unencrypted token
        TokenUser tokenUser = new TokenUser();
        tokenUser.setUserName(userName);
        tokenUser.setAuthToken(uuidStr);
        CacheManager.tokenUserMap.put(userName,tokenUser);
        // encrypted token, UserToken with encrypted Token
        UserToken userToken = new UserToken();
        userToken.setUserName(userName);
        userToken.setAuthToken(EncryptionUtil.encryptedWithRotatingHash(uuidStr));
        userToken.setCreatedTime(new Date());
        userToken.setExpiredTime(DateUtil.getHoursInForCurrentDate(userToken.getCreatedTime(),2));
        CacheManager.sessionMap.put(userToken.getAuthToken(),userToken);
        return uuidStr;
    }
}

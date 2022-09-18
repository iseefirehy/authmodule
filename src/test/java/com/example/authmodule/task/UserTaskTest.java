package com.example.authmodule.task;

import com.example.authmodule.entities.Users;
import com.example.authmodule.exception.GeneralException;
import com.example.authmodule.model.UserModel;
import com.example.authmodule.util.CacheManager;
import com.example.authmodule.util.EncryptionUtil;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Hongyu Zhang
 * @date 2022/9/18 20:58
 */
class UserTaskTest {

    @Inject
    private Instance<UserTask> userTasks;

    @Test
    void createUser() {
        UserModel userModel = new UserModel("test-1","asdfasdf123!");
        Users res = userTasks.get().createUserModel(userModel);
        Assertions.assertTrue(CacheManager.usersInfoMap.containsKey(res.getUserName()));
    }

    @Test
    void deleteUser() throws GeneralException {
        UserModel userModel = new UserModel("test-1","asdfasdf123!");
        Assertions.assertTrue(CacheManager.usersInfoMap.containsKey(userModel.getUserName()));
        Users users = new Users();
        users.setUserName(userModel.getUserName());
        String name = userTasks.get().deleteUser(users);
        Assertions.assertTrue(!CacheManager.usersInfoMap.containsKey(userModel.getUserName()));
    }

    @Test
    void invalidateSession() throws GeneralException {
        UserModel userModel = new UserModel("test-1","asdfasdf123!");
        Users res = userTasks.get().createUserModel(userModel);
        Assertions.assertTrue(CacheManager.usersInfoMap.containsKey(res.getUserName()));
        String token = userTasks.get().authenticateUser(userModel);
        Assertions.assertNotNull(token);

        userTasks.get().invalidateSession(token);
        Assertions.assertTrue(!CacheManager.sessionMap.containsKey(EncryptionUtil.encryptedWithRotatingHash(token)));
    }

    @Test
    void authenticateUser() throws GeneralException {
        UserModel userModel = new UserModel("test-1","asdfasdf123!");
        Users res = userTasks.get().createUserModel(userModel);
        Assertions.assertTrue(CacheManager.usersInfoMap.containsKey(res.getUserName()));
        String token = userTasks.get().authenticateUser(userModel);
        Assertions.assertNotNull(token);
    }
}
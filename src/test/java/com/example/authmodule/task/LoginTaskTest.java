package com.example.authmodule.task;

import com.example.authmodule.entities.Roles;
import com.example.authmodule.entities.Users;
import com.example.authmodule.exception.GeneralException;
import com.example.authmodule.model.UserModel;
import com.example.authmodule.util.CacheManager;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Hongyu Zhang
 * @date 2022/9/18 20:58
 */
class LoginTaskTest {

    @Inject
    private Instance<LoginTask> loginTasks;

    @Inject
    private Instance<UserTask> userTasks;

    @Test
    void createRole() throws GeneralException {
        Roles role = new Roles();
        role.setRoleId("1");
        role.setRoleName("guest");
        role.setDescription("Guest ONLY");
        String roleName = loginTasks.get().createRole(role);
        Assertions.assertTrue(CacheManager.rolesMap.containsKey(roleName));
    }

    @Test
    void deleteRole() throws GeneralException {
        Roles role = new Roles();
        role.setRoleId("1");
        role.setRoleName("guest");
        role.setDescription("Guest ONLY");
        String roleName = loginTasks.get().createRole(role);
        Assertions.assertTrue(CacheManager.rolesMap.containsKey(roleName));
        String roleNameDelete = loginTasks.get().deleteRole(role);
        Assertions.assertFalse(CacheManager.rolesMap.containsKey(roleNameDelete));
    }

    @Test
    void addRoleToUser() throws GeneralException {
        UserModel userModel = new UserModel("test-1","asdfasdf123!");
        Users res = userTasks.get().createUserModel(userModel);

        Roles role = new Roles();
        role.setRoleId("1");
        role.setRoleName("guest");
        role.setDescription("Guest ONLY");
        String roleName = loginTasks.get().createRole(role);

        loginTasks.get().addRoleToUser(res,roleName);
        Assertions.assertTrue(CacheManager.usersInfoMap.get(res.getUserName()).getRoleNames().contains(roleName));
    }

    @Test
    void checkRole() throws GeneralException {
        UserModel userModel = new UserModel("test-1","asdfasdf123!");
        Users res = userTasks.get().createUserModel(userModel);
        Assertions.assertTrue(CacheManager.usersInfoMap.containsKey(res.getUserName()));
        String token = userTasks.get().authenticateUser(userModel);
        Assertions.assertNotNull(token);

        Roles role = new Roles();
        role.setRoleId("1");
        role.setRoleName("guest");
        role.setDescription("Guest ONLY");
        String roleName = loginTasks.get().createRole(role);
        loginTasks.get().addRoleToUser(res,roleName);

        Assertions.assertTrue(loginTasks.get().checkRole(token,role).equals("true"));
    }

    @Test
    void allRoles() throws GeneralException {
        UserModel userModel = new UserModel("test-1","asdfasdf123!");
        Users res = userTasks.get().createUserModel(userModel);
        Assertions.assertTrue(CacheManager.usersInfoMap.containsKey(res.getUserName()));
        String token = userTasks.get().authenticateUser(userModel);
        Assertions.assertNotNull(token);

        Roles role = new Roles();
        role.setRoleId("1");
        role.setRoleName("guest");
        role.setDescription("Guest ONLY");
        String roleName = loginTasks.get().createRole(role);
        loginTasks.get().addRoleToUser(res,roleName);
        loginTasks.get().addRoleToUser(res,"admin");
        loginTasks.get().addRoleToUser(res,"staff");

        Assertions.assertNotNull(loginTasks.get().allRoles(token));

    }
}
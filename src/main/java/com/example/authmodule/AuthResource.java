package com.example.authmodule;

import com.example.authmodule.entities.Roles;
import com.example.authmodule.entities.Users;
import com.example.authmodule.exception.GeneralException;
import com.example.authmodule.model.UserModel;
import com.example.authmodule.task.LoginTask;
import com.example.authmodule.task.UserTask;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/authservice")
public class AuthResource {

    @Inject
    private Instance<LoginTask> loginTasks;

    @Inject
    private Instance<UserTask> userTask;

    @GET
    @Produces("text/plain")
    @Path("/answer")
    public String hello() {
        return "Hello World";
    }

    @POST
    @Path("/create/role")
    public String createRole(Roles role) throws GeneralException {
        return this.loginTasks.get().createRole(role);
    }

    @DELETE
    @Path("/delete/role")
    @Produces("application/json")
    public String deleteRole(Roles role) throws GeneralException {
        return this.loginTasks.get().deleteRole(role);
    }

    @POST
    @Path("/add/role")
    @Produces("application/json")
    public void addRoleToUser(Users users,@QueryParam("roleName") String roleName) throws GeneralException {
        this.loginTasks.get().addRoleToUser(users, roleName);
    }

    @POST
    @Path("/check/role")
    @Produces("application/json")
    public String checkRole(@QueryParam("authToken") String authToken, Roles role) throws GeneralException {
        return this.loginTasks.get().checkRole(authToken, role);
    }

    @GET
    @Path("/all/role")
    @Produces("application/json")
    public List<String> allRoles(@QueryParam("authToken") String authToken) throws GeneralException {
        return this.loginTasks.get().allRoles(authToken);
    }

    /**
     * create a normal user with userName and password
     * @param userModel
     * @return
     */
    @POST
    @Path("/create/user")
    public String createUser(UserModel userModel) throws GeneralException {
        return this.userTask.get().createUser(userModel);
    }

    /**
     * delete a user with username
     * @param user
     * @return
     */
    @DELETE
    @Path("/delete/user")
    public String deleteUser(Users user) throws GeneralException{
        return this.userTask.get().deleteUser(user);
    }

    @POST
    @Path("/authenticate/user")
    @Produces("application/json")
    public String authenticateUser(UserModel userModel) throws GeneralException {
        return this.userTask.get().authenticateUser(userModel);
    }

    @DELETE
    @Path("/invalidate/session")
    @Produces("application/json")
    public void invalidateSession(@QueryParam("authToken") String authToken) throws GeneralException {
        this.userTask.get().invalidateSession(authToken);
    }
}
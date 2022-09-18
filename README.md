# Authentication and Authorization Service

1. **Requirement and Background**

  This is a micro authentication and authorization service which involves the users profiles and access administration. Since Spring Frame is not allowed in this project. The Jakarta EE (Jave EE) component is being used in the project and most of the annotations (including @Resource, @Inject , @BeanParam are all provided for CDI and auto configuration purpose. The data persistence is not used in the project. Instead I used map to store all the information in memory. The encryption algorithm is not using any security class from Java, only hash functions are used in the project.
  
  
2. **Setup**

  Although the project is required to be a self-contained project and can run directly in IDE. But since it is not a spring project, the configuration for container is necessary. Here I suggest you to use Tomcat10.
  
3. **API List**

API Name|Url|Method Type|Description
-|-|-|-|-|-
**Create User**|POST|/api/authservice/create/user|Create user with user name and password in the system
**Delete User**|DELETE|/api/authservice/delete/user|Remove the user's information in the system
**Authenticate User**|POST|/api/authservice/authenticate/user|Authenticate with user name and input password.If success, return the valid auth token
**Invalidate Session**|DELETE|/api/authservice/invalidate/session|Invalidate the auth token of the user and user can't authencate again with previous token
**All Roles**|GET|/api/authservice/all/role?authToken|Return all the roles of related user which has the input auth token
**Check Role**|POST|/api/authservice/check/role|Check if the user has been assigned the role before.
**Add Role To User**|POST|/api/authservice/add/role|Assign certain role to the user
**Delete Role**|DELETE|/api/authservice/delete/role|Remove the role in the system
**Create Role**|POST|/api/authservice/create/role|Create the role in the system

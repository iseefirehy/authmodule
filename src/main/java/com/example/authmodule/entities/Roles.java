package com.example.authmodule.entities;

/**
 * @author Hongyu Zhang
 * @date 2022/9/17 23:34
 */

public class Roles {

    /**
     * Role Name
     */
    private String roleName;
    /**
     * Role Description
     */
    private String description;
    /**
     * Role id
     */
    private String roleId;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}

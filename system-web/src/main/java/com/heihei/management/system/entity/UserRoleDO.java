package com.heihei.management.system.entity;

/**
 * @ClassName UserRoleDO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/20 23:04
 **/
public class UserRoleDO {
    private int id;
    private int userId;
    private int roleId;

    public UserRoleDO() {
    }

    public UserRoleDO(int id, int userId, int roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRoleDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}

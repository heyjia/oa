package com.heihei.management.system.entity;

/**
 * @ClassName RolePrivilegeDO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/20 23:10
 **/
public class RolePrivilegeDO {
    private int id;
    private int roleId;
    private int privilegeId;

    public RolePrivilegeDO() {
    }

    public RolePrivilegeDO(int id, int roleId, int privilegeId) {
        this.id = id;
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Override
    public String toString() {
        return "RolePrivilegeDO{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", privilegeId=" + privilegeId +
                '}';
    }
}

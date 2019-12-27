package com.heihei.management.system.entity.form;

/**
 * @ClassName AddRoleForm
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/25 14:32
 **/
public class AddRoleForm {
    private int id;
    private String roleName;
    private String prvgIds;

    public AddRoleForm() {
    }

    public AddRoleForm(int id, String roleName, String prvgIds) {
        this.id = id;
        this.roleName = roleName;
        this.prvgIds = prvgIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPrvgIds() {
        return prvgIds;
    }

    public void setPrvgIds(String prvgIds) {
        this.prvgIds = prvgIds;
    }

    @Override
    public String toString() {
        return "AddRoleForm{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", prvgIds='" + prvgIds + '\'' +
                '}';
    }
}

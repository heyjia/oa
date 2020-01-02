package com.heihei.management.system.entity.form;

import java.util.Date;

/**
 * @ClassName UpdateUser
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/21 18:58
 **/
public class UpdateUserForm {
    private int id;
    private String name;
    private String telephone;
    private String email;
    private int sex;
    private int oldRoleId;
    private int newRoleId;
    private int oldDeptId;
    private int newDeptId;
    private int oldPostId;
    private int newPostId;
    private int oldSalary;
    private int newSalary;

    public UpdateUserForm() {
    }

    public UpdateUserForm(int id, String name, String telephone, String email, int sex, int oldRoleId, int newRoleId, int oldDeptId, int newDeptId, int oldPostId, int newPostId, int oldSalary, int newSalary) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.sex = sex;
        this.oldRoleId = oldRoleId;
        this.newRoleId = newRoleId;
        this.oldDeptId = oldDeptId;
        this.newDeptId = newDeptId;
        this.oldPostId = oldPostId;
        this.newPostId = newPostId;
        this.oldSalary = oldSalary;
        this.newSalary = newSalary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getOldRoleId() {
        return oldRoleId;
    }

    public void setOldRoleId(int oldRoleId) {
        this.oldRoleId = oldRoleId;
    }

    public int getNewRoleId() {
        return newRoleId;
    }

    public void setNewRoleId(int newRoleId) {
        this.newRoleId = newRoleId;
    }

    public int getOldDeptId() {
        return oldDeptId;
    }

    public void setOldDeptId(int oldDeptId) {
        this.oldDeptId = oldDeptId;
    }

    public int getNewDeptId() {
        return newDeptId;
    }

    public void setNewDeptId(int newDeptId) {
        this.newDeptId = newDeptId;
    }

    public int getOldPostId() {
        return oldPostId;
    }

    public void setOldPostId(int oldPostId) {
        this.oldPostId = oldPostId;
    }

    public int getNewPostId() {
        return newPostId;
    }

    public void setNewPostId(int newPostId) {
        this.newPostId = newPostId;
    }

    public int getOldSalary() {
        return oldSalary;
    }

    public void setOldSalary(int oldSalary) {
        this.oldSalary = oldSalary;
    }

    public int getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(int newSalary) {
        this.newSalary = newSalary;
    }

    @Override
    public String toString() {
        return "UpdateUserForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", oldRoleId=" + oldRoleId +
                ", newRoleId=" + newRoleId +
                ", oldDeptId=" + oldDeptId +
                ", newDeptId=" + newDeptId +
                ", oldPostId=" + oldPostId +
                ", newPostId=" + newPostId +
                ", oldSalary=" + oldSalary +
                ", newSalary=" + newSalary +
                '}';
    }
}

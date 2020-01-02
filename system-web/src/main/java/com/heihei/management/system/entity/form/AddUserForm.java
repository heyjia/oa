package com.heihei.management.system.entity.form;

import java.util.Date;

/**
 * @ClassName AddUserForm
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/21 15:17
 **/
public class AddUserForm {
    private String name;
    private String password;
    private String salt;
    private int sex;
    private String telephone;
    private String email;
    private int roleId;
    private int postId;
    private int deptId;
    private float salary;
    public AddUserForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "AddUserForm{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", sex=" + sex +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", postId=" + postId +
                ", deptId=" + deptId +
                ", salary=" + salary +
                '}';
    }
}

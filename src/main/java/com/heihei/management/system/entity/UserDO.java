package com.heihei.management.system.entity;

import java.util.Date;

/**
 * @ClassName userDO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/20 22:57
 **/
public class UserDO {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String telephone;
    private String email;
    private int sex;
    private Date crtTime;
    private Date updtTime;

    public UserDO() {
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

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Date getUpdtTime() {
        return updtTime;
    }

    public void setUpdtTime(Date updtTime) {
        this.updtTime = updtTime;
    }

    public UserDO(int id, String name, String password, String salt, String telephone, String email, int sex, Date crtTime, Date updtTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.telephone = telephone;
        this.email = email;
        this.sex = sex;
        this.crtTime = crtTime;
        this.updtTime = updtTime;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", crtTime=" + crtTime +
                ", updtTime=" + updtTime +
                '}';
    }
}

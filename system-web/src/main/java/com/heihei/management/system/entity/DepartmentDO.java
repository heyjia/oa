package com.heihei.management.system.entity;

import java.util.Date;

/**
 * @ClassName DepartmentDO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/20 23:00
 **/
public class DepartmentDO {
    private int id;
    private int pid;
    private String name;
    private String address;
    private String describe;
    private Date crtTime;
    private Date updtTime;

    public DepartmentDO() {
    }

    public DepartmentDO(int id, int pid, String name, String address, String describe, Date crtTime, Date updtTime) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.address = address;
        this.describe = describe;
        this.crtTime = crtTime;
        this.updtTime = updtTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

    @Override
    public String toString() {
        return "DepartmentDO{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", describe='" + describe + '\'' +
                ", crtTime=" + crtTime +
                ", updtTime=" + updtTime +
                '}';
    }
}

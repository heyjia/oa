package com.heihei.management.system.entity.vo;

import java.util.Date;

/**
 * @ClassName DeptVO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/23 9:47
 **/
public class DeptVO {
    private int id;
    private int pid;
    private String name;
    private String pName;
    private String address;
    private String describe;
    private Date crtTime;
    private Date updtTime;

    public DeptVO() {
    }

    public DeptVO(int id, int pid, String name, String pName, String address, String describe, Date crtTime, Date updtTime) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.pName = pName;
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

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
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
        return "DeptVO{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", pName='" + pName + '\'' +
                ", address='" + address + '\'' +
                ", describe='" + describe + '\'' +
                ", crtTime=" + crtTime +
                ", updtTime=" + updtTime +
                '}';
    }
}

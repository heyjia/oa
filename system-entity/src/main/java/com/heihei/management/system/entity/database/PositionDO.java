package com.heihei.management.system.entity.database;

import java.util.Date;

/**
 * @ClassName PositionDO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/20 23:05
 **/
public class PositionDO {
    private int id;
    private String name;
    private String describe;
    private Date crtTime;
    private Date updtTime;
    public PositionDO() {
    }

    public PositionDO(int id, String name, String describe, Date crtTime, Date updtTime) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "PositionDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", crtTime=" + crtTime +
                ", updtTime=" + updtTime +
                '}';
    }
}

package com.heihei.management.system.entity;

/**
 * @ClassName RoleDO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/20 22:59
 **/
public class RoleDO {
    private int id;
    private String name;

    public RoleDO() {
    }

    public RoleDO(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "RoleDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

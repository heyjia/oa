package com.heihei.management.system.entity.vo;

/**
 * @ClassName TreeVO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/22 21:58
 **/
public class TreeVO {
    private int id;
    private int pid;
    private String name;
    private boolean open;

    public TreeVO() {
    }

    public TreeVO(int id, int pid, String name, boolean open) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.open = open;
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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "TreeVO{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", open=" + open +
                '}';
    }
}

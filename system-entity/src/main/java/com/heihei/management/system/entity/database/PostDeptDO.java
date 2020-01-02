package com.heihei.management.system.entity.database;

/**
 * @ClassName PostDeptDO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/20 23:12
 **/
public class PostDeptDO {
    private int id;
    private int deptId;
    private int postId;

    public PostDeptDO() {
    }

    public PostDeptDO(int id, int deptId, int postId) {
        this.id = id;
        this.deptId = deptId;
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}

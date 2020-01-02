package com.heihei.management.system.entity.database;

/**
 * @ClassName UserPost
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/20 23:08
 **/
public class UserPostDO {
    private int id;
    private int userId;
    private int postId;
    private int dept_id;
    private float salary;

    public UserPostDO() {
    }

    public UserPostDO(int id, int userId, int postId, int dept_id, float salary) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.dept_id = dept_id;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "UserPostDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                ", dept_id=" + dept_id +
                ", salary=" + salary +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}

package com.heihei.management.system.entity.vo;

/**
 * @ClassName DeptPosiVO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/21 10:27
 **/
public class DeptPosiVO{
    private int deptId;
    private String deptName;
    private int postId;
    private String postName;
    private float salary;

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "DeptPosiVO{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", postId=" + postId +
                ", postName='" + postName + '\'' +
                ", salary=" + salary +
                '}';
    }
}

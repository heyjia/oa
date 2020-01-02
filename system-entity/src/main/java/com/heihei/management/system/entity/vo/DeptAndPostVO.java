package com.heihei.management.system.entity.vo;

import com.heihei.management.system.entity.database.DepartmentDO;
import com.heihei.management.system.entity.database.PositionDO;

import java.util.List;

/**
 * @ClassName DeptAndPostVO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/23 14:26
 **/
public class DeptAndPostVO {
    private DepartmentDO dept;
    private List<PositionDO> posts;

    public DeptAndPostVO(DepartmentDO dept, List<PositionDO> posts) {
        this.dept = dept;
        this.posts = posts;
    }

    public DeptAndPostVO() {
    }

    public DepartmentDO getDept() {
        return dept;
    }

    public void setDept(DepartmentDO dept) {
        this.dept = dept;
    }

    public List<PositionDO> getPosts() {
        return posts;
    }

    public void setPosts(List<PositionDO> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "DeptAndPostVO{" +
                "dept=" + dept +
                ", posts=" + posts +
                '}';
    }
}

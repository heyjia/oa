package com.heihei.management.system.entity.vo;

import com.heihei.management.system.entity.database.RoleDO;
import com.heihei.management.system.entity.database.UserDO;

import java.util.List;

/**
 * @ClassName UserListVO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/21 10:26
 **/
public class UserListVO {
    private UserDO user;
    private List<DeptPosiVO> deptPosis;
    private List<RoleDO> roles;

    public UserListVO() {
    }

    public UserListVO(UserDO user, List<DeptPosiVO> deptPosis, List<RoleDO> roles) {
        this.user = user;
        this.deptPosis = deptPosis;
        this.roles = roles;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }

    public List<DeptPosiVO> getDeptPosis() {
        return deptPosis;
    }

    public void setDeptPosis(List<DeptPosiVO> deptPosis) {
        this.deptPosis = deptPosis;
    }

    public List<RoleDO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDO> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserListVO{" +
                "user=" + user +
                ", deptPosis=" + deptPosis +
                ", roles=" + roles +
                '}';
    }
}

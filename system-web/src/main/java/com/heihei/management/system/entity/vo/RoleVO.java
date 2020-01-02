package com.heihei.management.system.entity.vo;

import com.heihei.management.system.entity.PrivilegeDO;
import com.heihei.management.system.entity.RoleDO;

import java.util.List;

/**
 * @ClassName RoleVO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/25 11:25
 **/
public class RoleVO {
    private RoleDO role;
    private List<PrivilegeDO> prvgs;

    public RoleVO() {
    }

    public RoleVO(RoleDO role, List<PrivilegeDO> prvgs) {
        this.role = role;
        this.prvgs = prvgs;
    }

    public RoleDO getRole() {
        return role;
    }

    public void setRole(RoleDO role) {
        this.role = role;
    }

    public List<PrivilegeDO> getPrvgs() {
        return prvgs;
    }

    public void setPrvgs(List<PrivilegeDO> prvgs) {
        this.prvgs = prvgs;
    }

    @Override
    public String toString() {
        return "RoleVO{" +
                "role=" + role +
                ", prvgs=" + prvgs +
                '}';
    }
}

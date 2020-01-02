package com.heihei.management.system.entity.vo;

import com.heihei.management.system.entity.database.PrivilegeDO;
import com.heihei.management.system.entity.database.RoleDO;

import java.util.List;

/**
 * @ClassName RolePrvgVO
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/25 14:59
 **/
public class RolePrvgVO {
    private RoleDO role;
    private List<PrivilegeDO> prvgs;

    public RolePrvgVO() {
    }

    public RolePrvgVO(RoleDO role, List<PrivilegeDO> prvgs) {
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
        return "RolePrvgVO{" +
                "role=" + role +
                ", prvgs=" + prvgs +
                '}';
    }
}

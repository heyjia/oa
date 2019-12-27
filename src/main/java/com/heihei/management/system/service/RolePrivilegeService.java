package com.heihei.management.system.service;

import com.heihei.management.system.entity.RolePrivilegeDO;

import java.util.List;

/**
 * RolePrivilegeService
 * @Description    角色权限中间表service层接口
 * @author CHENZEJIA
 * @date 2019/12/17
 */

public interface RolePrivilegeService {
    //根据角色id查询角色权限中间表数据
    //public List<RolePrivilegeDO> selectRolePrivilegeByRoleId(int roleId);

    //删除角色与权限的关联
    int deleteRolePrivilegeByRoleId(int roleId);
}

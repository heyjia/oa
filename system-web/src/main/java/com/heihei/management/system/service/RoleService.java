package com.heihei.management.system.service;

import com.heihei.management.system.entity.RoleDO;

import java.util.List;

/**
 * RoleService
 * @Description    角色的service层接口
 * @author CHENZEJIA
 * @date 2019/12/17
 */
public interface RoleService {
    // 根据用户的id查询用户的角色
    List<RoleDO> selectRolesByUserId(int userId);

    // 查询所有的角色
    List<RoleDO> selectRoles();

    // 根据角色id查询角色
    RoleDO getRoleById(int roleId);

    // 模糊查询所有的角色
    List<RoleDO> selectRolesByTip(String selectTip);

    // 删除角色
    int deleteRoleByRoleId(int roleId);

    // 根据条件模糊查询角色
    List<RoleDO> selectRoleByTip(String selectTip);

    // 根据用户名查看角色
    RoleDO getRoleByName(String roleName);

    // 添加角色
    int addRole(RoleDO role);

     // 给角色添加权限
    int addPrvgToRole(int id, int prvgId);

    //更新角色
    int updateRole(RoleDO role);
}

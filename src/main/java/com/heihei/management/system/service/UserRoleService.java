package com.heihei.management.system.service;



import com.heihei.management.system.entity.UserRoleDO;

import java.util.List;

/**
 * UserRoleService
 * @Description    用户角色service层接口
 * @author CHENZEJIA
 * @date 2019/12/17
 */

public interface UserRoleService {
    //根据用户id查询用户角色中间表数据
    List<UserRoleDO> selectUserRoleByUserId(int userId);
    //对中间表插入数据，添加用户的角色
    boolean addUserRole(int userId, int roleId);
    //对中间表删除数据，删除用户的角色
    boolean deleteUserRole(int userId);
    //更新用户的角色
    int updateUserRole(int oldRoleId, int newRoleId, int id);
    //根据角色id表删除数据，删除用户的角色
    int deleteUserRoleByRoleId(int roleId);
}

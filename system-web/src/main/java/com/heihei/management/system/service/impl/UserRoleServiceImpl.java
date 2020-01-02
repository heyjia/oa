package com.heihei.management.system.service.impl;

import com.heihei.management.system.dao.UserRoleDao;
import com.heihei.management.system.entity.UserRoleDO;
import com.heihei.management.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserRoleService
 * @Description    用户角色service层
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleDao userRoleDao;
    //根据用户id查询用户角色中间表数据
    public List<UserRoleDO> selectUserRoleByUserId(int userId) {
        List<UserRoleDO> userRoles = userRoleDao.selectUserRolesByUserId(userId);
        return userRoles;
    }
    //对中间表插入数据，添加用户的角色
    public boolean addUserRole(int userId,int roleId) {
        int id = userRoleDao.addUserRole(userId,roleId);
        if (id > 0) {
            return true;
        }else{
            return false;
        }
    }
    //对中间表删除数据，删除用户的角色
    public boolean deleteUserRole(int userId) {
        int userRoleId = userRoleDao.deleteUserRole(userId);
        if (userRoleId > 0) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int updateUserRole(int oldRoleId, int newRoleId, int userId) {
        int item  = userRoleDao.updateUserRole(oldRoleId,newRoleId,userId);
        return item;
    }

    @Override
    public int deleteUserRoleByRoleId(int roleId) {
        int item  = userRoleDao.deleteUserRoleByRoleId(roleId);
        return item;
    }


}

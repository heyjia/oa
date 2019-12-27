package com.heihei.management.system.service.impl;

import com.heihei.management.system.dao.RoleDao;
import com.heihei.management.system.entity.RoleDO;
import com.heihei.management.system.entity.UserRoleDO;
import com.heihei.management.system.service.RolePrivilegeService;
import com.heihei.management.system.service.RoleService;
import com.heihei.management.system.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * RoleService
 * @Description    角色的service层
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Service
public class RoleServiceImpl implements RoleService {
    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    RoleDao roleDao;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RolePrivilegeService rolePrivilegeService;
    //根据用户的id查询用户的角色
    public List<RoleDO> selectRolesByUserId(int userId) {
        logger.info("进入selectRolesByUserId()查询用户的角色");
        List<RoleDO> roles = roleDao.selectRoleByUserId(userId);
        return roles;
    }

    @Override
    public List<RoleDO> selectRoles() {
        List<RoleDO> roles = roleDao.selectRoles();
        for (int i = 0;i < roles.size();i++) {
            logger.info(roles.get(i).toString());
        }
        return roles;
    }

    @Override
    public RoleDO getRoleById(int roleId) {
        RoleDO role = roleDao.getRoleById(roleId);
        logger.info(role.toString());
        return role;
    }

    @Override
    public List<RoleDO> selectRolesByTip(String selectTip) {
        List<RoleDO> roles = roleDao.selectRolesByTip(selectTip);
        for (int i = 0;i < roles.size();i++) {
            logger.info(roles.get(i).toString());
        }
        return roles;
    }

    @Override
    public int deleteRoleByRoleId(int roleId) {
        //先删除用户与角色的关联
        userRoleService.deleteUserRoleByRoleId(roleId);
        //再删除角色与权限的关联
        rolePrivilegeService.deleteRolePrivilegeByRoleId(roleId);
        //再删除角色
        int item = roleDao.deleteRoleByRoleId(roleId);
        return item;
    }
    //根据条件模糊查询角色
    @Override
    public List<RoleDO> selectRoleByTip(String selectTip) {
        return roleDao.selectRoleByTip(selectTip);
    }
    // 根据用户名查角色
    @Override
    public RoleDO getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);
    }

    //添加角色
    @Override
    public int addRole(RoleDO role) {
        return roleDao.addRole(role);
    }

    // 给角色添加权限
    @Override
    public int addPrvgToRole(int roleId, int prvgId) {
        return roleDao.addPrvgToRole(roleId,prvgId);
    }

    //更新角色
    @Override
    public int updateRole(RoleDO role) {
        return roleDao.updateRole(role);
    }

}
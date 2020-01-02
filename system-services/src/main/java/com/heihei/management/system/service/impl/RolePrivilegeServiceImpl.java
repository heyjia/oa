package com.heihei.management.system.service.impl;


import com.heihei.management.system.dao.RolePrivilegeDao;
import com.heihei.management.system.service.RolePrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RolePrivilegeService
 * @Description    角色权限中间表service层
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {
    @Autowired
    RolePrivilegeDao rolePrivilegeDao;

    @Override
    public int deleteRolePrivilegeByRoleId(int roleId) {
        int item = rolePrivilegeDao.deleteRolePrivilegeByRoleId(roleId);
        return item;
    }
}

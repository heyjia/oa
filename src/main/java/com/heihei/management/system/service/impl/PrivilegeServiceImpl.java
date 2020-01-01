package com.heihei.management.system.service.impl;
;
import com.heihei.management.system.dao.PrivilegeDao;
import com.heihei.management.system.entity.PrivilegeDO;
import com.heihei.management.system.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * PrivilegeService
 * @Description         权限的service层
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    PrivilegeDao privilegeDao;
    // 查询所有的权限
    @Override
    public List<PrivilegeDO> listPrivilege() {
        List<PrivilegeDO> privileges = privilegeDao.listPrivilege();
        return privileges;
    }
    // 根据角色id查询所有的权限
    @Override
    public List<PrivilegeDO> listPrivilegeByRoleId(int roleId) {
        List<PrivilegeDO> privileges = privilegeDao.listPrivilegeByRoleId(roleId);
        return privileges;
    }

    @Override
    public List<PrivilegeDO> selectPostByTip(String selectTip) {
        List<PrivilegeDO> privileges = privilegeDao.selectPostByTip(selectTip);
        return privileges;
    }
    //根据权限名查询权限
    @Override
    public PrivilegeDO getPrvgByPrvgName(String name) {
        PrivilegeDO privilege = privilegeDao.getPrvgByPrvgName(name);
        return privilege;
    }
    // 添加权限
    @Override
    public int addPrvg(PrivilegeDO privilegeDO) {
        return privilegeDao.addPrvg(privilegeDO);
    }

    @Override
    public int deletePrvgRoleByPrvgId(int prvgId) {
        return privilegeDao.deletePrvgRoleByPrvgId(prvgId);
    }

    @Override
    public int deletePrvgByPrvgId(int prvgId) {
        return privilegeDao.deletePrvgByPrvgId(prvgId);
    }
    // 根据权限id查询权限
    @Override
    public PrivilegeDO selectPrvgByPrvgId(int prvgId) {
        return privilegeDao.selectPrvgByPrvgId(prvgId);
    }

    @Override
    public int updateDept(PrivilegeDO privilegeDO) {
        return privilegeDao.updateDept(privilegeDO);
    }

    @Override
    public int deletePrvgRoleByRoleId(int roleId) {
        return privilegeDao.deletePrvgRoleByRoleId(roleId);
    }


}

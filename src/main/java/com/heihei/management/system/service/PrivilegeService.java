package com.heihei.management.system.service;

import com.heihei.management.system.entity.PrivilegeDO;

import java.util.List;

/**
 * PrivilegeService
 * @Description         权限的service层接口
 * @author CHENZEJIA
 * @date 2019/12/17
 */
public interface PrivilegeService {
    //查询所有的权限配置
    List<PrivilegeDO> listPrivilege();
    //根据用户id查询权限
    List<PrivilegeDO> listPrivilegeByRoleId(int id);
    //模糊查询权限
    List<PrivilegeDO> selectPostByTip(String selectTip);
    //根据权限名查询权限
    PrivilegeDO getPrvgByPrvgName(String name);
    //添加权限
    int addPost(PrivilegeDO privilegeDO);
    //删除权限和角色的联系
    int deletePrvgRoleByPrvgId(int prvgId);
    //删除权限
    int deletePrvgByPrvgId(int prvgId);
    //查询权限PrvgId
    PrivilegeDO selectPrvgByPrvgId(int prvgId);
    //更新权限
    int updateDept(PrivilegeDO privilegeDO);
    //删除角色和权限的联系
    int deletePrvgRoleByRoleId(int roleId);
}

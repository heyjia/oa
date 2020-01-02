package com.heihei.management.system.service.impl;

import com.heihei.management.system.dao.UserDao;
import com.heihei.management.system.entity.database.RoleDO;
import com.heihei.management.system.entity.database.UserDO;
import com.heihei.management.system.entity.form.AddUserForm;
import com.heihei.management.system.entity.form.UpdateUserForm;
import com.heihei.management.system.entity.vo.DeptPosiVO;
import com.heihei.management.system.entity.vo.UserListVO;
import com.heihei.management.system.service.PostService;
import com.heihei.management.system.service.RoleService;
import com.heihei.management.system.service.UserRoleService;
import com.heihei.management.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UserService
 * @Description 用户的service层
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserDao userDao;
    @Autowired
    RoleService roleService;
    @Autowired
    PostService postService;
    @Autowired
    UserRoleService userRoleService;
//    @Autowired
//    UserRoleService userRoleService;
//    @Autowired
//    RoleService roleService;
    //根据用户名查询用户
    public UserListVO getUserByUserName(String userName) {
        UserDO user = userDao.selectUserByUserName(userName);
        logger.info(user.toString());
        List<RoleDO> roels = new ArrayList<>();
        roels = roleService.selectRolesByUserId(user.getId());
        for (int j = 0;j < roels.size();j++) {
            logger.info(roels.get(j).toString());
        }
        List<DeptPosiVO> deptPosis = new ArrayList<>();
        deptPosis = postService.listDeptPosi(user.getId());
        for (int k = 0;k < deptPosis.size();k++) {
            logger.info(deptPosis.get(k).toString());
        }
        UserListVO userVO = new UserListVO();
        userVO.setDeptPosis(deptPosis);
        userVO.setRoles(roels);
        userVO.setUser(user);
        return userVO;
    }

    @Override
    public List<UserListVO> listUser() {
        logger.info("进入listUser()方法");
        List<UserListVO> userList = new ArrayList<>();
        List<UserDO> users = userDao.listUser();
        for (int i = 0; i < users.size(); i++) {
            UserDO user = users.get(i);
            logger.info(user.toString());
            List<RoleDO> roels = new ArrayList<>();
            roels = roleService.selectRolesByUserId(user.getId());
            for (int j = 0;j < roels.size();j++) {
                logger.info(roels.get(j).toString());
            }
            List<DeptPosiVO> deptPosis = new ArrayList<>();
            deptPosis = postService.listDeptPosi(user.getId());
            for (int k = 0;k < deptPosis.size();k++) {
                logger.info(deptPosis.get(k).toString());
            }
            UserListVO userVO = new UserListVO();
            userVO.setDeptPosis(deptPosis);
            userVO.setRoles(roels);
            userVO.setUser(user);
            userList.add(userVO);
        }
        return userList;
     }
    //根据用户的id查询用户的信息
    @Override
    public UserListVO getUserByUserId(int userId) {
        UserDO user = userDao.getUserByUserId(userId);
        logger.info(user.toString());
        List<RoleDO> roels = new ArrayList<>();
        roels = roleService.selectRolesByUserId(user.getId());
        for (int j = 0;j < roels.size();j++) {
            logger.info(roels.get(j).toString());
        }
        List<DeptPosiVO> deptPosis = new ArrayList<>();
        deptPosis = postService.listDeptPosi(user.getId());
        for (int k = 0;k < deptPosis.size();k++) {
            logger.info(deptPosis.get(k).toString());
        }
        UserListVO userVO = new UserListVO();
        userVO.setDeptPosis(deptPosis);
        userVO.setRoles(roels);
        userVO.setUser(user);
        return userVO;
    }

    @Override
    public UserDO getOnlyUserByUserName(String userName) {
        return userDao.selectUserByUserName(userName);
    }

    //个人中心更新用户
    @Override
    public boolean updateUser(UserDO userDO) {
        logger.info("个人中心更新用户方法");
        int item = userDao.updateUser(userDO);
        if (item > 0) {
            return true;
        }else{
            return false;
        }
    }

    //管理员更新用户信息
    public boolean updateUser(UpdateUserForm updateUserForm) {
        logger.info("进入updateUser方法");
        UserDO user = userDao.selectUserByUserName(updateUserForm.getName());
        if (user.getTelephone() != updateUserForm.getTelephone()) {
            user.setTelephone(updateUserForm.getTelephone());
        }
        if (user.getEmail() != updateUserForm.getEmail()) {
            user.setEmail(updateUserForm.getEmail());
        }
        user.setUpdtTime(new Date());
        user.setSex(updateUserForm.getSex());
        userDao.updateUser(user);
        int oldRoleId = updateUserForm.getOldRoleId();
        int newRoleId = updateUserForm.getNewRoleId();
        int oldDeptId = updateUserForm.getOldDeptId();
        int newDeptId = updateUserForm.getNewDeptId();
        int oldPostId = updateUserForm.getOldPostId();
        int newPostId = updateUserForm.getNewPostId();
        int oldSalary = updateUserForm.getOldSalary();
        int newSalary = updateUserForm.getNewSalary();
        if (oldRoleId != newRoleId){
            userRoleService.updateUserRole(oldRoleId,newRoleId,user.getId());
        }
        if (oldDeptId != newDeptId || oldPostId != newPostId || oldSalary != newSalary) {
            postService.updatePost(updateUserForm);
        }
        return true;
    }

    @Override
    public int countUser() {
        return userDao.countUser();
    }

    //修改用户密码
    public boolean  updatePassword(String newPassword,int userId) {
        int temp = userDao.updatePassword(newPassword,userId);
        if (temp <= 0) {
            return false;
        }
        return true;
    }
    //添加用户，事务，先添加用户，并且默认给他User的角色
    @Transactional
    public boolean addUser(AddUserForm addUserForm) {
        UserDO user = new UserDO();
        user.setName(addUserForm.getName());
        user.setEmail(addUserForm.getEmail());
        user.setPassword(addUserForm.getPassword());
        user.setTelephone(addUserForm.getTelephone());
        user.setSex(addUserForm.getSex());
        user.setCrtTime(new Date());
        user.setUpdtTime(new Date());
        int userId = userDao.addUser(user);     //在User表中添加用户
        logger.info("userId： " + user.getId());
        logger.info("userId2： " + userId);
        if (userId > 0 && (addUserForm.getRoleId() > 0)) {
            boolean addresult = userRoleService.addUserRole(user.getId(),addUserForm.getRoleId());      //User_Role表，添加用户的角色
            if (addresult == false) {
                return false;
            }
        }
        if (userId > 0 && (addUserForm.getPostId() > 0) && (addUserForm.getDeptId() > 0)) {
            int postId = addUserForm.getPostId();
            int deptId = addUserForm.getDeptId();
            float salary = addUserForm.getSalary();
            boolean addresult = postService.addPostDept(user.getId(),postId,deptId,salary);
            if (addresult == false) {
                return false;
            }
        }
        return true;
    }
    //事务删除用户，先删除用户角色，再删除用用户的信息
    @Transactional
    public boolean deleteUser(int userId) {
         userRoleService.deleteUserRole(userId);    //删除用户角色
        // 删除用户的岗位联系
        postService.deleteUserPost(userId);
        int id = userDao.deleteUser(userId);                    //删除用户信息
        if (id > 0){
            return true;
        }
        return false;
    }
    //根据查询条件模糊查询用户
    public List<UserListVO> selectAllUserByTip(String selectTip) {
        List<UserDO> users = userDao.selectUserBySelectTip(selectTip);   //模糊查询用户
        List<UserListVO> userList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            UserDO user = users.get(i);
            logger.info(user.toString());
            List<RoleDO> roels = new ArrayList<>();
            roels = roleService.selectRolesByUserId(user.getId());
            for (int j = 0;j < roels.size();j++) {
                logger.info(roels.get(j).toString());
            }
            List<DeptPosiVO> deptPosis = new ArrayList<>();
            deptPosis = postService.listDeptPosi(user.getId());
            for (int k = 0;k < deptPosis.size();k++) {
                logger.info(deptPosis.get(k).toString());
            }
            UserListVO userVO = new UserListVO();
            userVO.setDeptPosis(deptPosis);
            userVO.setRoles(roels);
            userVO.setUser(user);
            userList.add(userVO);
        }
        return userList;
    }


}

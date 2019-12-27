package com.heihei.management.system.service;


import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.entity.form.AddUserForm;
import com.heihei.management.system.entity.form.UpdateUserForm;
import com.heihei.management.system.entity.vo.UserListVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserService
 * @Description 用户的service层接口
 * @author CHENZEJIA
 * @date 2019/12/17
 */
public interface UserService {
    //根据用户名查询用户
    UserListVO getUserByUserName(String userName);
    //查询所有的用户，以及其对应的角色，以及岗位和部门薪资
    List<UserListVO> listUser();
    //根据用户的id查询用户
    UserListVO getUserByUserId(int userId);
    //根据用户名查询用户
    UserDO getOnlyUserByUserName(String userName);
    // 更新用户信息
     boolean updateUser(UserDO userDO) ;
    //修改用户密码
     boolean  updatePassword(String newPassword, int userId);
    //添加用户，事务，先添加用户，并且默认给他User的角色和岗位
    @Transactional
    public boolean addUser(AddUserForm addUserForm) ;
    //事务删除用户，先删除用户角色，再删除用户岗位，再删除用户的信息
    @Transactional
    boolean deleteUser(int userId);
    //根据查询条件模糊查询用户
    List<UserListVO> selectAllUserByTip(String selectTip);
    //更新用户信息
    boolean updateUser(UpdateUserForm updateUserForm);
    //获取用户的条数
    int countUser();
}

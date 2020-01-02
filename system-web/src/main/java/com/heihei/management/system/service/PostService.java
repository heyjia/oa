package com.heihei.management.system.service;

import com.heihei.management.system.entity.PositionDO;
import com.heihei.management.system.entity.form.UpdateUserForm;
import com.heihei.management.system.entity.vo.DeptPosiVO;

import java.util.List;

/**
 * @ClassName PosiService
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/21 11:11
 **/
public interface PostService {
    List<DeptPosiVO> listDeptPosi(int userId);

    boolean deleteUserPost(int userId);

    boolean addPostDept(int id, int postId, int deptId,float salary);

    void updatePost(UpdateUserForm updateUserForm);

    int addPost(PositionDO positionDO);

    int deleteUserPostByDeptId(int deptId);
    //根据部门的id查询其所有的岗位
    List<PositionDO> listPostByDeptId(int deptId);
    //获取所有的岗位
    List<PositionDO> listPost();
    //删除部门的岗位根据部门id
    int deletePostByDeptId(int deptId);
    //模糊查询岗位
    List<PositionDO> selectPostByTip(String selectTip);
    //根据部门查询部门
    PositionDO getPostByPostName(String postName);
    //根据岗位id删除岗位和部门的联系
    int deleteDeptPostByPostId(int postId);
    //根据岗位id删除用户和岗位的联系
    int deleteUserPostByPostId(int postId);
    //根据岗位id删除岗位
    int deletePostByPostId(int postId);
    //根据岗位id查询岗位
    PositionDO getPostByPostId(int postId);
    //更新岗位信息
    int updatePostByForm(PositionDO toUpdatePost);
}

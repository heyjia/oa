package com.heihei.management.system.service.impl;

import com.heihei.management.system.dao.PostDao;
import com.heihei.management.system.entity.PositionDO;
import com.heihei.management.system.entity.form.UpdateUserForm;
import com.heihei.management.system.entity.vo.DeptPosiVO;
import com.heihei.management.system.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PosiServiceImpl
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/21 11:14
 **/
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostDao postDao;
    @Override
    public List<DeptPosiVO> listDeptPosi(int userId) {
        List<DeptPosiVO> deptPosis =  postDao.listUserDeptPostByUserId(userId);
        return deptPosis;
    }

    @Override
    public boolean deleteUserPost(int userId) {
        int userRoleId = postDao.deleteUserPost(userId);
        if (userRoleId >= 0) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean addPostDept(int id, int postId, int deptId,float salary) {
        int num = postDao.addPostDept(id,postId,deptId,salary);
        if (num > 0) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updatePost(UpdateUserForm updateUserForm) {
        postDao.updatePost(updateUserForm);
    }

    @Override
    public int addPost(PositionDO positionDO) {
        int item = postDao.addPost(positionDO);
        return item;
    }

    @Override
    public int deleteUserPostByDeptId(int deptId) {
        int item = postDao.deleteUserPostByDeptId(deptId);
        return item;
    }

    @Override
    public List<PositionDO> listPostByDeptId(int deptId) {
        List<PositionDO> posts = postDao.listPostByDeptId(deptId);
        return posts;
    }

    @Override
    public List<PositionDO> listPost() {
        List<PositionDO> posts = postDao.listPost();
        return posts;
    }

    @Override
    public int deletePostByDeptId(int deptId) {
        int item = postDao.deletePostByDeptId(deptId);
        return item;
    }

    @Override
    public List<PositionDO> selectPostByTip(String selectTip) {
        List<PositionDO> pst = postDao.selectPostByTip(selectTip);
        return pst;
    }

    @Override
    public PositionDO getPostByPostName(String postName) {
        PositionDO post = postDao.getPostByPostName(postName);
        return post;
    }

    @Override
    public int deleteDeptPostByPostId(int postId) {
        int item = postDao.deleteDeptPostByPostId(postId);
        return item;
    }

    @Override
    public int deleteUserPostByPostId(int postId) {
        int item = postDao.deleteUserPostByPostId(postId);
        return item;
    }

    @Override
    public int deletePostByPostId(int postId) {
        int item = postDao.deletePostByPostId(postId);
        return item;
    }

    @Override
    public PositionDO getPostByPostId(int postId) {
        PositionDO post = postDao.getPostByPostId(postId);
        return post;
    }

    @Override
    public int updatePostByForm(PositionDO toUpdatePost) {
        int item =  postDao.updatePostByForm(toUpdatePost);
        return item;
    }


}

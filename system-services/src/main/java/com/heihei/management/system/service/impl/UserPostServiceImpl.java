package com.heihei.management.system.service.impl;

import com.heihei.management.system.dao.PostDao;
import com.heihei.management.system.entity.database.UserPostDO;
import com.heihei.management.system.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserPostServiceImpl
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/21 11:18
 **/
@Service
public class UserPostServiceImpl implements UserPostService {
    @Autowired
    PostDao postDao;
    public List<UserPostDO> listUserPost(int userId){
        List<UserPostDO> userPosts = postDao.listUserPost(userId);
        return userPosts;
    }
}

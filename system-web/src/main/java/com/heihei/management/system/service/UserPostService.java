package com.heihei.management.system.service;

import com.heihei.management.system.entity.UserPostDO;

import java.util.List;

/**
 * @ClassName UserPostService
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/21 11:17
 **/

public interface UserPostService {
    List<UserPostDO> listUserPost(int userId);
}

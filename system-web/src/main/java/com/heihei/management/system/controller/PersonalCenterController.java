package com.heihei.management.system.controller;

import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.entity.vo.UserListVO;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.service.UserService;
import com.heihei.management.system.utils.RSAUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @ClassName PersonalCenterController
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/22 14:34
 **/
@Controller()
@RequestMapping(value = "/personal")
public class PersonalCenterController {
    Logger logger = LoggerFactory.getLogger(PersonalCenterController.class);
    @Autowired
    UserService userService;
    @RequestMapping(value = "/toPersonalCenter")
    public String toPersonalCenter(Model model){
        logger.info("前往个人中心，toPersonalCenter方法");
        UserDO user = (UserDO) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        UserListVO userListVo = userService.getUserByUserId(user.getId());
        logger.info(userListVo.getUser().toString());
        model.addAttribute("uservo",userListVo);
        UserDO u = (UserDO)SecurityUtils.getSubject().getSession().getAttribute("userSession");
        model.addAttribute("u",u);
        return "demo/personalCenter";
    }

    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public Result<Boolean> updateUser(@RequestParam int userSex,@RequestParam String telephone,@RequestParam String hiddenUserName,@RequestParam String email) {
        logger.info("进入更新用户方法");
        logger.info(userSex+"");
        logger.info(telephone);
        logger.info(hiddenUserName);
        logger.info(email);
        UserDO user = userService.getOnlyUserByUserName(hiddenUserName);
        user.setSex(userSex);
        user.setEmail(email);
        user.setTelephone(telephone);
        user.setUpdtTime(new Date());
        boolean result = userService.updateUser(user);
        if (result == false){
            return Result.error(CodeMsg.UPDATEUSER_ERROR);
        }
        SecurityUtils.getSubject().getSession().setAttribute("userSession",user);
        return Result.success(true);
    }
    // /user/updatePassword 修改用户的密码，在缓存中获取用户的信息，并且判断密码是否正确，更新缓存
    @RequestMapping(value = "/alterPwd")
    @ResponseBody
    public Result<Boolean> alterPwd(@RequestParam String oldPassword,@RequestParam String  newPassword) {
        logger.info("修改密码...");
        UserDO user = (UserDO)SecurityUtils.getSubject().getSession().getAttribute("userSession");
        logger.info(user.toString());
        //对输入的旧密码进行解密
        String inputOldPasswordEn = RSAUtil.decrypt(oldPassword,RSAUtil.PRIVATE_KEY);
        logger.info("输入解密码解码后结果：" + inputOldPasswordEn);
        //对原密码进行解密
        String userOldPasswordEn = RSAUtil.decrypt(user.getPassword(),RSAUtil.PRIVATE_KEY);
        logger.info("数据库旧密码解码后结果：" + userOldPasswordEn);
        if (!inputOldPasswordEn .equals(userOldPasswordEn)) {
            logger.info("旧密码错误...");
            return Result.error(CodeMsg.OLDPASSWORD_ERROR);
        }
        boolean result = userService.updatePassword(newPassword, user.getId());   //数据库更新密码
        if (result == false) {
            return Result.error(CodeMsg.UPDATEPASSWORD_ERROR);
        }
        user.setPassword(newPassword);
        SecurityUtils.getSubject().getSession().setAttribute("userSession", user);   //更新缓存
        return Result.success(true);
    }
}

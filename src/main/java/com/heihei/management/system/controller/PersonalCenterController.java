package com.heihei.management.system.controller;

import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.entity.vo.UserListVO;
import com.heihei.management.system.redis.RedisService;
import com.heihei.management.system.redis.UserKeyPerfix;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.service.UserService;
import com.heihei.management.system.util.RSAUtil;
import com.heihei.management.system.util.UserCookieUtil;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    UserCookieUtil userCookieUtil;
    @Autowired
    RedisService redisService;
    @RequestMapping(value = "/toPersonalCenter")
    public String toPersonalCenter(Model model,UserDO user){
        logger.info("前往个人中心，toPersonalCenter方法");
        UserListVO userListVo = userService.getUserByUserId(user.getId());
        logger.info(userListVo.getUser().toString());
        model.addAttribute("uservo",userListVo);
        model.addAttribute("u",user);
        return "demo/personalCenter";
    }

    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public Result<Boolean> updateUser(HttpServletRequest request,@RequestParam int userSex, @RequestParam String telephone, @RequestParam String hiddenUserName, @RequestParam String email, UserDO u) {
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
        flushUser(request,user);
        return Result.success(true);
    }
    // /user/updatePassword 修改用户的密码，在缓存中获取用户的信息，并且判断密码是否正确，更新缓存
    @RequestMapping(value = "/alterPwd")
    @ResponseBody
    public Result<Boolean> alterPwd(HttpServletRequest request,@RequestParam String oldPassword,@RequestParam String  newPassword,UserDO user) {
        logger.info("修改密码...");
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
        flushUser(request,user);
        return Result.success(true);
    }
    public void flushUser(HttpServletRequest request,UserDO user) {
        Cookie[] cookies = request.getCookies();
        String cookievalue = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(userCookieUtil.COOKIE_NAME_TOKEN)) {
                cookievalue = cookie.getValue();
            }
        }
        redisService.set(UserKeyPerfix.userKeyPerfix,cookievalue,user);
    }
}

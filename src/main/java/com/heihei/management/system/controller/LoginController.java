package com.heihei.management.system.controller;

import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.util.RSAUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
/**
 * LoginController
 * @Description      登录controller
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    ThymeleafViewResolver thymleafViewResolver;
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    // 拦截用户请求，跳转到登录页面
    @RequestMapping("/")
    public String test() {
        return "demo/login";
    }
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "demo/login";
    }

    // 拦截用户登录请求，用shiro进行认证，判断用户是否存在以及用户密码是否正确
    @RequestMapping(value = "/doLogin")
    @ResponseBody
    public Result<Boolean> doLogin(@RequestParam(name = "userName") String userName, @RequestParam(name = "password") String password){
        logger.info("进入doLogin");
        password = RSAUtil.decrypt(password,RSAUtil.PRIVATE_KEY);
        logger.info("表单密码解密后的结果：" + password);
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password);    //将用户名包装成一个token
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);                                                     //执行登录请求，会被shiro过滤器拦截，对用户信息进行认证
        }catch (IncorrectCredentialsException ice){
            return  Result.error(CodeMsg.PASSWORD_ERROR);
        }catch (UnknownAccountException uae){
            return  Result.error(CodeMsg.UNKNOWACCOUNT);
        }catch (Exception e) {
            //其他错误登录异常
            return Result.error(new CodeMsg(10003,e.getMessage()));
        }
        if (subject.isAuthenticated()) {
            logger.info("登录成功");
        }
        return Result.success(true);
    }

    // 前往首页
    @RequestMapping("/toIndex")
    public String toIndex(Model model){
        UserDO user = (UserDO)SecurityUtils.getSubject().getSession().getAttribute("userSession");
        model.addAttribute("u",user);
        logger.info("toLogin方法：前往首页");
        return "demo/index";
    }

    //登出请求
    @RequestMapping(value = "/logout")
    public String logout() {
        logger.info("logout：注销");
        Subject subject = SecurityUtils.getSubject();     //logout登录
        subject.logout();
        return "demo/login";
    }
    //获取公钥getPublicKey
    @RequestMapping(value = "/getPublicKey")
    @ResponseBody
    public String getPublicKey() {
        return RSAUtil.PUBLIC_KEY;
    }

}

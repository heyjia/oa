package com.heihei.management.system.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.redis.IPKeyPerfix;
import com.heihei.management.system.redis.RedisService;
import com.heihei.management.system.redis.UserKeyPerfix;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.util.IPUtil;
import com.heihei.management.system.util.UserCookieUtil;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class IPInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(IPInterceptor.class);
    @Autowired
    UserCookieUtil userCookieUtil;
    @Autowired
    RedisService redisService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("TestInterceptor preHandle....");
        //判断请求是ajax还是直接跳转
        boolean ajax = isAjax(request);
        logger.info("[preHandle]-ajax:" + ajax);
        //先在cookie中查找用户的信息,获取token
        Cookie[] cookies = request.getCookies();
        String cookievalue = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(userCookieUtil.COOKIE_NAME_TOKEN)) {
                cookievalue = cookie.getValue();
            }
        }
        //根据token获取用户的信息
        UserDO user = redisService.get(UserKeyPerfix.userKeyPerfix,cookievalue,UserDO.class);
        logger.info("[preHandle]-userInfo:" + user.toString());
        //根据用户名查询用户的ip
        String userRedisIP = "";
        userRedisIP = redisService.get(IPKeyPerfix.ipKeyPerfix,user.getName(),String.class);
        logger.info("[preHandle]-userRedisIP:" + userRedisIP);
        //获取请求的ip
        String requestIP = IPUtil.getIP(request);
        logger.info("[preHandle]-requestIP:" + requestIP);
        //如果相等，则放行，否则返回错误信息
        if (userRedisIP.equals(requestIP)) {
            return true;
        }else{
            if (ajax == true) {
                Result result = Result.error(CodeMsg.IP_ERROR);
                returnJson(response,result);
            }else{
                String url = request.getContextPath() + "/login/toLogin?flag=false";
                logger.info("[preHandle]-url:" + url);

                response.sendRedirect(url);
            }
            return false;
        }
    }

    public void returnJson(HttpServletResponse response,Result result) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        try {
            writer = response.getWriter();
            writer.print(JSONObject.toJSONString(result));
        } catch (IOException e) {
            logger.error("response error",e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    private boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest"
                .equals(request.getHeader("X-Requested-With").toString()));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}

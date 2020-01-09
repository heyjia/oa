package com.heihei.management.system.config;

import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.util.UserCookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserConfigurationResolver implements HandlerMethodArgumentResolver {
    @Autowired
    UserCookieUtil userCookieUtil;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == UserDO.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response =  webRequest.getNativeResponse(HttpServletResponse.class);
        String paramvalue = request.getParameter(userCookieUtil.COOKIE_NAME_TOKEN);
        Cookie[] cookies = request.getCookies();
        String cookievalue = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(userCookieUtil.COOKIE_NAME_TOKEN)) {
                cookievalue = cookie.getValue();
            }
        }
        if (cookievalue == null && paramvalue == null) {
            return null;
        }
        String key = paramvalue == null ? cookievalue : paramvalue;
        UserDO user = userCookieUtil.getCookieValue(response,key);
        return user;
    }
}

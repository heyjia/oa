package com.heihei.management.system.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class IPInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(IPInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("TestInterceptor preHandle....");
        Result result = Result.error(CodeMsg.IP_ERROR);
        //returnJson(response,result);
        return true;
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

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        logger.info("TestInterceptor postHandle....");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        logger.info("TestInterceptor afterCompletion....");
    }
}

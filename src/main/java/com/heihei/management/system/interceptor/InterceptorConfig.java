package com.heihei.management.system.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    IPInterceptor ipInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipInterceptor)
                .addPathPatterns("/login/doLogin");
    }
}

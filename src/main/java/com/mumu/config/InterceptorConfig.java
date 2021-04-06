package com.mumu.config;

import com.mumu.filter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    //不会被登录拦截的路径包括：
    private static final String[] excludePath = {"/", "/login", "/checkUser", "/findPass", "/sendMsg",
            "/findPassWord", "/toRegister", "/register", "/js/**", "/img/**", "/fonts/**", "/css/**"};

    @Resource
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns(excludePath)
                .addPathPatterns("/**");
    }
}

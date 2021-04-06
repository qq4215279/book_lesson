package com.mumu.filter;

import com.mumu.utils.CookiesUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override   //前置拦截器
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在登录的时候在客户端存了一个叫“token”名字的cookie值，
       boolean flag = CookiesUtils.isContainToken(CookiesUtils.Token_Name, request);//Token_Name = "token";
       if (!flag){
           response.sendRedirect( request.getContextPath() + "/" );
       }
       return flag;
    }
}

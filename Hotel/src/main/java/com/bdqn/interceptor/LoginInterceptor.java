package com.bdqn.interceptor;

import com.bdqn.utils.SystemConstant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断session是否为空，也就是是否已有用户登录
        if(request.getSession().getAttribute(SystemConstant.LOGINUSER) == null){
            //如果没有登录，直接定向到登录页面
            response.sendRedirect(request.getContextPath()+"/admin/login.html");
            return false;
        }
        return true;
    }
}

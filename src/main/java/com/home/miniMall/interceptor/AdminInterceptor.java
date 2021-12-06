package com.home.miniMall.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //登录页放行
        if ("/admin/login.action".equals(request.getServletPath())){
            return true;
        }

        //session中有admin对象放行
        if (request.getSession().getAttribute("admin")!=null){
            return true;
        }

        //请求转发到登录页
        response.sendRedirect(request.getContextPath()+"/admin/login.jsp");
        return false;
    }
}

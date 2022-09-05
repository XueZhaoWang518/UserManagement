package com.spring.usermanagement.security.jwt;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final String[] IGNORE_URL = {"/signup", "/signin","/test"};

    @Autowired
    JwtUtils jwtUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception
    {
        System.out.println("AuthorizationInterceptor preHandle --> ");
        // flag变量用于判断用户是否登录，默认为false
        boolean flag = false;
        //获取请求的路径进行判断
        String servletPath = request.getPathInfo();
        // 判断请求是否需要拦截
        for (String s : IGNORE_URL) {
            if (servletPath.contains(s)) {
                flag = true;
                break;
            }
        }

        // 拦截请求
        if (!flag){
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validToken(jwt)) {
                System.out.println("AuthorizationInterceptor放行请求：");
                flag = true;
            }
            else{
                System.out.println("AuthorizationInterceptor拦截请求：");
                request.setAttribute("message", "请先登录再访问网站");
                request.getRequestDispatcher("loginForm").forward(request, response);
            }
        }
        return flag;
    }

    private String parseJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7, header.length());
        }
        return null;
    }
}

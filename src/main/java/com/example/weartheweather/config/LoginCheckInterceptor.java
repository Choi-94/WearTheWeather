package com.example.weartheweather.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        if (session.getAttribute("loginEmail") == null) {
            response.sendRedirect("/member/login?redirectURI=" + requestURI);
            return false;
        } else {
            String loginEmail = (String) session.getAttribute("loginEmail");
            if (loginEmail.equals("gkdudquf1@naver.com")) {
                return true;
            } else {
                response.sendRedirect("/access-denied");
                return false;
            }
        }
    }
}
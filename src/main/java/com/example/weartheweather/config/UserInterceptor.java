package com.example.weartheweather.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginEmail") != null) {
            // 로그인한 사용자 처리
            return true;
        } else {
            // 로그인하지 않은 사용자 처리
            String requestURI = request.getRequestURI();
            String redirectURI = URLEncoder.encode(requestURI, "UTF-8");
            session.setAttribute("originalRequestURI", redirectURI);
            response.sendRedirect("/member/memberLogin");
            return false;
        }
    }
}
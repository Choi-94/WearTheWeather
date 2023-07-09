package com.example.weartheweather.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

// 로그인 여부를 확인하고 로그인 상태라면 사용자가 요청한 주소로 보내고
// 로그인하지 않은 상태라면 컨트롤러의 로그인 요청 주소로 넘김
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        if (session.getAttribute("loginEmail") == null) {
            String redirectURI = URLEncoder.encode(requestURI, "UTF-8");
            session.setAttribute("originalRequestURI", redirectURI); // Store the original request URL
            response.sendRedirect("/member/memberLogin");
            return false;
        } else {
            String loginEmail = (String) session.getAttribute("loginEmail");
            if (loginEmail.equals("gkdudquf@naver.com")) {
                return true;
            } else {
                response.sendRedirect("/access-denied");
                return false;
            }
        }
    }
}
package com.example.weartheweather.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 로그인 여부를 확인하고 로그인 상태라면 사용자가 요청한 주소로 보내고
// 로그인하지 않은 상태라면 컨트롤러의 로그인 요청 주소로 넘김
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        // 사용자가 요청한 주소 확인
        String requestURI = request.getRequestURI();
        System.out.println("requestURL = " + requestURI);
        // 세션객체 생성
        HttpSession session = request.getSession();
        // 세션에 저장된 로그인 정보 확인
        if (session.getAttribute("loginEmail") == null) {
            // 로그인하지 않았다면 로그인 페이지로 보내면서
            // 요청한 주소값도 같이 보냄
            response.sendRedirect("/member/memberLogin?redirectURI=" + requestURI);
            return false;
        } else {
            // 로그인 상태일 경우 관리자 아이디와 비밀번호 확인
            String loginEmail = (String) session.getAttribute("loginEmail");
            if (loginEmail.equals("gkdudquf@naver.com")) {
                // 로그인한 사용자가 관리자인 경우 요청한 페이지로 이동
                return true;
            } else {
                // 일반 사용자로 인식되어 접근 차단
                response.sendRedirect("/access-denied");
                return false;
            }
        }
    }
}

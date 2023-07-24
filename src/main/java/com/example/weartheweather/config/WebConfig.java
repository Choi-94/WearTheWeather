package com.example.weartheweather.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**"; // HTML에서 접근할 경로
    private String savePath = "file:///C:/data/weather_img/"; // 실제 파일이 저장된 경로

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath).addResourceLocations(savePath);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor()) // 인터셉터로 등록할 클래스
                .order(1) // 해당 인터셉터의 우선순위
                .addPathPatterns("/admin/**","/adminBoard/save") // 인터셉터로 체크할 주소(관리자 페이지)
                .excludePathPatterns(
                        "/", "/img/**", "/error", "/member/**", "/mail/**", "/market/list",
                        "/market/list/{id}", "/memberBoard/list", "/member/myBoardList",
                        "/memberBoard/ranking", "/member/memberLogin", "/member/login/axios",
                        "/adminBoard/list", "/js/**", "/css/**", "/images/**", "/image/**"
                        , "/member/logout", "/static/**", "/upload/**",
                        "/market/{id}", "/auth/**", "/*.ico", "/favicon/**"
                ); // 인터셉터 검증을 하지 않을 주소
        registry.addInterceptor(new UserInterceptor()) // 사용자 인터셉터로 등록할 클래스
                .order(2) // 해당 인터셉터의 우선순위
                .addPathPatterns("/**") // 인터셉터로 체크할 주소(모든 페이지)
                .excludePathPatterns(
                        "/", "/img/**", "/error", "/member/**", "/mail/**", "/market/list",
                        "/market/list/{id}", "/memberBoard/list", "/member/myBoardList",
                        "/memberBoard/ranking", "/member/memberLogin", "/member/login/axios",
                        "/adminBoard/list", "/js/**", "/css/**", "/images/**", "/image/**"
                        , "/member/logout", "/static/**", "/upload/**","/adminBoard/detail/{id}",
                        "/market/{id}", "/auth/**", "/*.ico", "/favicon/**"
                ); // 인터셉터 검증을 하지 않을 주소
    }

}

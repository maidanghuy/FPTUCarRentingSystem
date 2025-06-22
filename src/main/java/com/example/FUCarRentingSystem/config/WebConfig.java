package com.example.FUCarRentingSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthInterceptor jwtAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor)
                .addPathPatterns("/customer/**") // ✅ Chỉ chặn giao diện web
                .excludePathPatterns(
                        "/auth/**",         // Cho phép trang login
                        "/css/**", "/js/**", "/images/**", // Tài nguyên tĩnh
                        "/api/**"           // ✅ Không chặn API
                );
    }
}


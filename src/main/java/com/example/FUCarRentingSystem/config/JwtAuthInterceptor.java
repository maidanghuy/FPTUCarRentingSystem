package com.example.FUCarRentingSystem.config;

import com.example.FUCarRentingSystem.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {

        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("accessToken")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            response.sendRedirect("http://localhost:8080/de180293/auth/login");
            return false;
        }

        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            String role = (String) claims.get("role");

            String requestURI = request.getRequestURI();

            if (requestURI.startsWith("/de180293/admin") && !"ADMIN".equalsIgnoreCase(role)) {
                response.sendRedirect("/de180293/home");
                return false;
            }

            return true;

        } catch (Exception e) {
            response.sendRedirect("/de180293/auth/login");
            return false;
        }
    }

}

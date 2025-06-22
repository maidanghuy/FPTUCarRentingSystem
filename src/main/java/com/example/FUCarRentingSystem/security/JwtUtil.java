package com.example.FUCarRentingSystem.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // Khóa bí mật dùng để ký token (nên lưu trong biến môi trường thực tế)
    private static final String SECRET_KEY = "my-super-secret-key-for-jwt-signature-which-should-be-very-long";

    // Token hết hạn sau X mili giây (mặc định 1 ngày)
    private static final long DEFAULT_EXPIRATION_MS = 24 * 60 * 60 * 1000;

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Tạo token có thời hạn tuỳ chọn
    public String generateToken(Map<String, Object> claims, long expirationMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Tạo token với thời hạn mặc định
    public String generateToken(Map<String, Object> claims) {
        return generateToken(claims, DEFAULT_EXPIRATION_MS);
    }

    // Giải mã token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String extractId(String token) {
        return extractAllClaims(token).get("id", String.class);
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

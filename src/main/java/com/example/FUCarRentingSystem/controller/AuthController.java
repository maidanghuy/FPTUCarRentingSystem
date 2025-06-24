package com.example.FUCarRentingSystem.controller;

import com.example.FUCarRentingSystem.dto.request.LoginRequest;
import com.example.FUCarRentingSystem.dto.request.RegisterRequest;
import com.example.FUCarRentingSystem.dto.response.APIResponse;
import com.example.FUCarRentingSystem.dto.response.AuthResponse;
import com.example.FUCarRentingSystem.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/register")
    public APIResponse<AuthResponse> register(@RequestBody RegisterRequest request) {
        return APIResponse.<AuthResponse>builder()
                .result(authService.register(request))
                .build();
    }
    // Sample JSON
    /*
        {
            "accountName": "nguyenvana",
            "email": "nguyenvana@example.com",
            "password": "SecurePass123",
            "customerName": "Nguyễn Văn A",
            "mobile": "0912345678",
            "birthday": "1998-06-21T00:00:00",
            "identityCard": "123456789",
            "licenceNumber": "B123456",
            "licenceDate": "2018-05-10T00:00:00"
        }
    */
    @PostMapping("/login")
    public ResponseEntity<APIResponse<AuthResponse>> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.login(request);

        // Gán JWT vào cookie
        ResponseCookie cookie = ResponseCookie.from("accessToken", authResponse.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofHours(24)) // hoặc Duration.ofSeconds(x)
                .sameSite("Strict")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Trả về thông tin không bao gồm token
        AuthResponse safeResponse = new AuthResponse(authResponse.getToken(), authResponse.getUsername(), authResponse.getRole());

        return ResponseEntity.ok(APIResponse.<AuthResponse>builder()
                .result(safeResponse)
                .build());
    }

    // Sample JSON CUSTOMER
    /*
        {
            "email": "nguyenvana@example.com",
            "password": "SecurePass123"
        }
    */

    // Sample JSON ADMIN
    /*
        {
            "email": "maidanghuy2222@gmail.com",
            "password": "********"
        }
    */

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("Logged out");
    }

}

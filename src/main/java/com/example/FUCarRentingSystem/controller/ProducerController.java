package com.example.FUCarRentingSystem.controller;

import com.example.FUCarRentingSystem.dto.request.CarProductRequest;
import com.example.FUCarRentingSystem.dto.response.APIResponse;
import lombok.RequiredArgsConstructor;
import com.example.FUCarRentingSystem.dto.response.CarProductResponse;
import com.example.FUCarRentingSystem.service.ProducerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.FUCarRentingSystem.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/car-product")
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerService producerService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public APIResponse<List<CarProductResponse>> getAllProducts() {
        return APIResponse.<List<CarProductResponse>>builder()
                .result(producerService.getAllProducts())
                .build();
    }

    @PutMapping("/{id}")
    public APIResponse<CarProductResponse> updateProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable String id,
            @RequestBody CarProductRequest req) {
        String jwt = token.replace("Bearer ", "");
        String role = jwtUtil.extractRole(jwt);
        if (!"ADMIN".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update product");
        }
        return APIResponse.<CarProductResponse>builder()
                .result(producerService.updateProduct(id, req))
                .build();
    }
    // Sample JSON
    /*
     * {
     * "producerName": "Toyota",
     * "address": "Korea",
     * "country": "South Korea"
     * }
     */
}

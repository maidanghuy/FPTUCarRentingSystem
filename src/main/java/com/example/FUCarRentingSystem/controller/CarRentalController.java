package com.example.FUCarRentingSystem.controller;

import com.example.FUCarRentingSystem.dto.request.CreateCarRentalRequest;
import com.example.FUCarRentingSystem.dto.response.APIResponse;
import com.example.FUCarRentingSystem.entity.CarRental;
import com.example.FUCarRentingSystem.service.CarRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/rentals")
@RequiredArgsConstructor
public class CarRentalController {

    private final CarRentalService carRentalService;


    @PostMapping
    public APIResponse<?> createCarRental(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateCarRentalRequest request) {

        List<CarRental> rentals = carRentalService.createRentalTransaction(token, request);

        return APIResponse.<List<CarRental>>builder()
                .result(rentals)
                .build();
    }
}

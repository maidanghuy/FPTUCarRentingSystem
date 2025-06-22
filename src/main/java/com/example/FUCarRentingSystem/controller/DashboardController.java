package com.example.FUCarRentingSystem.controller;

import com.example.FUCarRentingSystem.dto.response.APIResponse;
import com.example.FUCarRentingSystem.dto.response.RentalReportResponse;
import com.example.FUCarRentingSystem.entity.CarRental;
import com.example.FUCarRentingSystem.service.CarRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    @Autowired
    CarRentalService carRentalService;

    // http://localhost:8080/de180293/api/dashboard/rentals/report?startDate=2025-06-01&endDate=2025-06-30
    @GetMapping("/rentals/report")
    public APIResponse<?> getRentalReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<RentalReportResponse> report = carRentalService.getRentalReportBetween(startDate, endDate);
        return APIResponse.<List<RentalReportResponse>>builder()
                .result(report)
                .build();
    }
}

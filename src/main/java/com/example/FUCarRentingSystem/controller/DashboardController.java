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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        Page<RentalReportResponse> reportPage;
        if (startDate == null || endDate == null) {
            reportPage = carRentalService.getAllRentalReportsPaged(PageRequest.of(page, size));
        } else {
            reportPage = carRentalService.getRentalReportBetweenPaged(startDate, endDate, PageRequest.of(page, size));
        }
        return APIResponse.<Page<RentalReportResponse>>builder()
                .result(reportPage)
                .build();
    }

    @PutMapping("/rentals/{rentalId}/mark-rented")
    public APIResponse<?> markRentalAsRented(@RequestHeader("Authorization") String token,
            @PathVariable String rentalId) {
        carRentalService.markRentalAsRented(token, rentalId);
        return APIResponse.builder().result("Rental marked as RENTED").build();
    }
}

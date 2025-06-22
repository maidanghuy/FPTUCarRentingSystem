package com.example.FUCarRentingSystem.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalReportResponse {
    private String rentalId;
    private String carName;
    private String customerName;
    private LocalDateTime pickupDate;
    private LocalDateTime returnDate;
    private BigDecimal rentPrice;
    private String status;
}

package com.example.FUCarRentingSystem.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarRentalItemRequest {
    String carId;
    LocalDateTime pickupDate;
    LocalDateTime returnDate;
}

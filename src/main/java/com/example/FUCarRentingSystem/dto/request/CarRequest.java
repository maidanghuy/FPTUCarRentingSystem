package com.example.FUCarRentingSystem.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarRequest {
    String carName;
    int carModelYear;
    String color;
    int capacity;
    String description;
    LocalDateTime importDate;
    String producerId;
    BigDecimal rentPrice;
    String status;
}

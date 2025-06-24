package com.example.FUCarRentingSystem.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarProductResponse {
    String producerId;
    String producerName;
    String address;
    String country;
}
package com.example.FUCarRentingSystem.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    String customerName;
    String mobile;
    LocalDateTime birthday;
    String email;
    String identityCard;
    String licenceNumber;
    LocalDateTime licenceDate;
}

package com.example.FUCarRentingSystem.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    String accountName;

    String email;
    String password;

    String customerName;
    String mobile;
    LocalDateTime birthday;
    String identityCard;
    String licenceNumber;
    LocalDateTime licenceDate;
}

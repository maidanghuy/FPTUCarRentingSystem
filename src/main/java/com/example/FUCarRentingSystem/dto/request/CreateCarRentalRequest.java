package com.example.FUCarRentingSystem.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCarRentalRequest {
    private List<CarRentalItemRequest> rentalItems;

}

package com.example.FUCarRentingSystem.mapper;

import com.example.FUCarRentingSystem.dto.response.RentalReportResponse;
import com.example.FUCarRentingSystem.entity.CarRental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarRentalMapper {

        @Mapping(source = "rentalId", target = "rentalId")
        @Mapping(source = "car.carName", target = "carName")
        @Mapping(source = "customer.customerName", target = "customerName")
        @Mapping(source = "pickupDate", target = "pickupDate")
        @Mapping(source = "returnDate", target = "returnDate")
        @Mapping(source = "rentPrice", target = "rentPrice")
        @Mapping(source = "status", target = "status")
        RentalReportResponse toRentalReport(CarRental rental);
        List<RentalReportResponse> toRentalReportList(List<CarRental> rentals);
}

package com.example.FUCarRentingSystem.mapper;

import com.example.FUCarRentingSystem.dto.response.RentalReportResponse;
import com.example.FUCarRentingSystem.entity.Car;
import com.example.FUCarRentingSystem.entity.CarRental;
import com.example.FUCarRentingSystem.entity.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-18T16:39:15+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class CarRentalMapperImpl implements CarRentalMapper {

    @Override
    public RentalReportResponse toRentalReport(CarRental rental) {
        if ( rental == null ) {
            return null;
        }

        RentalReportResponse.RentalReportResponseBuilder rentalReportResponse = RentalReportResponse.builder();

        rentalReportResponse.rentalId( rental.getRentalId() );
        rentalReportResponse.carName( rentalCarCarName( rental ) );
        rentalReportResponse.customerName( rentalCustomerCustomerName( rental ) );
        rentalReportResponse.pickupDate( rental.getPickupDate() );
        rentalReportResponse.returnDate( rental.getReturnDate() );
        rentalReportResponse.rentPrice( rental.getRentPrice() );
        rentalReportResponse.status( rental.getStatus() );

        return rentalReportResponse.build();
    }

    @Override
    public List<RentalReportResponse> toRentalReportList(List<CarRental> rentals) {
        if ( rentals == null ) {
            return null;
        }

        List<RentalReportResponse> list = new ArrayList<RentalReportResponse>( rentals.size() );
        for ( CarRental carRental : rentals ) {
            list.add( toRentalReport( carRental ) );
        }

        return list;
    }

    private String rentalCarCarName(CarRental carRental) {
        if ( carRental == null ) {
            return null;
        }
        Car car = carRental.getCar();
        if ( car == null ) {
            return null;
        }
        String carName = car.getCarName();
        if ( carName == null ) {
            return null;
        }
        return carName;
    }

    private String rentalCustomerCustomerName(CarRental carRental) {
        if ( carRental == null ) {
            return null;
        }
        Customer customer = carRental.getCustomer();
        if ( customer == null ) {
            return null;
        }
        String customerName = customer.getCustomerName();
        if ( customerName == null ) {
            return null;
        }
        return customerName;
    }
}

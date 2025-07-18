package com.example.FUCarRentingSystem.mapper;

import com.example.FUCarRentingSystem.dto.response.CustomerResponse;
import com.example.FUCarRentingSystem.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponse toCustomerResponse(Customer customer);
}

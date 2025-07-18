package com.example.FUCarRentingSystem.mapper;

import com.example.FUCarRentingSystem.dto.response.CustomerResponse;
import com.example.FUCarRentingSystem.entity.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-18T16:39:15+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerResponse toCustomerResponse(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerResponse.CustomerResponseBuilder customerResponse = CustomerResponse.builder();

        customerResponse.birthday( customer.getBirthday() );
        customerResponse.customerId( customer.getCustomerId() );
        customerResponse.customerName( customer.getCustomerName() );
        customerResponse.email( customer.getEmail() );
        customerResponse.identityCard( customer.getIdentityCard() );
        customerResponse.licenceDate( customer.getLicenceDate() );
        customerResponse.licenceNumber( customer.getLicenceNumber() );
        customerResponse.mobile( customer.getMobile() );

        return customerResponse.build();
    }
}

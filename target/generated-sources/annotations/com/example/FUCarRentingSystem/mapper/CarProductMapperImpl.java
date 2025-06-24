package com.example.FUCarRentingSystem.mapper;

import com.example.FUCarRentingSystem.dto.response.CarProductResponse;
import com.example.FUCarRentingSystem.entity.CarProducer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-24T14:06:05+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class CarProductMapperImpl implements CarProductMapper {

    @Override
    public CarProductResponse toCarProductResponse(CarProducer producer) {
        if ( producer == null ) {
            return null;
        }

        CarProductResponse.CarProductResponseBuilder carProductResponse = CarProductResponse.builder();

        carProductResponse.producerId( producer.getProducerId() );
        carProductResponse.producerName( producer.getProducerName() );
        carProductResponse.address( producer.getAddress() );
        carProductResponse.country( producer.getCountry() );

        return carProductResponse.build();
    }

    @Override
    public List<CarProductResponse> toCarProductResponseList(List<CarProducer> producers) {
        if ( producers == null ) {
            return null;
        }

        List<CarProductResponse> list = new ArrayList<CarProductResponse>( producers.size() );
        for ( CarProducer carProducer : producers ) {
            list.add( toCarProductResponse( carProducer ) );
        }

        return list;
    }
}

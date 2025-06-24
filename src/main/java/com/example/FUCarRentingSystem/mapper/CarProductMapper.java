package com.example.FUCarRentingSystem.mapper;

import com.example.FUCarRentingSystem.dto.response.CarProductResponse;
import com.example.FUCarRentingSystem.entity.CarProducer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarProductMapper {
    @Mapping(source = "producerId", target = "producerId")
    @Mapping(source = "producerName", target = "producerName")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "country", target = "country")
    CarProductResponse toCarProductResponse(CarProducer producer);

    List<CarProductResponse> toCarProductResponseList(List<CarProducer> producers);
}
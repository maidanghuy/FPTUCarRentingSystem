package com.example.FUCarRentingSystem.service;

import com.example.FUCarRentingSystem.dto.request.CarProductRequest;
import com.example.FUCarRentingSystem.dto.response.CarProductResponse;
import com.example.FUCarRentingSystem.entity.CarProducer;
import com.example.FUCarRentingSystem.exception.AppException;
import com.example.FUCarRentingSystem.exception.ErrorCode;
import com.example.FUCarRentingSystem.mapper.CarProductMapper;
import com.example.FUCarRentingSystem.repository.ICarProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ICarProducerRepository carProducerRepository;
    private final CarProductMapper carProductMapper;

    public List<CarProductResponse> getAllProducts() {
        return carProductMapper.toCarProductResponseList(carProducerRepository.findAll());
    }

    public CarProductResponse updateProduct(String id, CarProductRequest req) {
        CarProducer producer = carProducerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCER_NOT_FOUND));
        producer.setProducerName(req.getProducerName());
        producer.setAddress(req.getAddress());
        producer.setCountry(req.getCountry());
        carProducerRepository.save(producer);
        return carProductMapper.toCarProductResponse(producer);
    }
}
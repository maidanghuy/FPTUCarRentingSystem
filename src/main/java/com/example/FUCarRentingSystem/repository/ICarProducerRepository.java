package com.example.FUCarRentingSystem.repository;

import com.example.FUCarRentingSystem.entity.CarProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarProducerRepository  extends JpaRepository<CarProducer, String> {
}

package com.example.FUCarRentingSystem.repository;

import com.example.FUCarRentingSystem.entity.Car;
import com.example.FUCarRentingSystem.entity.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICarRepository extends JpaRepository<Car, String> {
    Optional<Car> findCarsByCarId(String carId);
}

package com.example.FUCarRentingSystem.repository;

import com.example.FUCarRentingSystem.entity.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICarRentalRepository extends JpaRepository<CarRental, String> {
    Optional<CarRental> findTopByCar_CarIdOrderByPickupDateDesc(String carId);
    List<CarRental> findByCustomer_CustomerIdOrderByPickupDateDesc(String customerId);
    List<CarRental> findByPickupDateBetweenOrderByPickupDateDesc(LocalDateTime startDate, LocalDateTime endDate);
    boolean existsByCar_CarIdAndPickupDateLessThanEqualAndReturnDateGreaterThanEqual(
            String carId, LocalDateTime returnDate, LocalDateTime pickupDate);
}

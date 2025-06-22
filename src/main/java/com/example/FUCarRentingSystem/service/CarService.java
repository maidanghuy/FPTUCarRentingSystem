package com.example.FUCarRentingSystem.service;

import com.example.FUCarRentingSystem.dto.request.CarRequest;
import com.example.FUCarRentingSystem.entity.Car;
import com.example.FUCarRentingSystem.entity.CarProducer;
import com.example.FUCarRentingSystem.entity.CarRental;
import com.example.FUCarRentingSystem.exception.AppException;
import com.example.FUCarRentingSystem.exception.ErrorCode;
import com.example.FUCarRentingSystem.repository.ICarProducerRepository;
import com.example.FUCarRentingSystem.repository.ICarRentalRepository;
import com.example.FUCarRentingSystem.repository.ICarRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarService {
    ICarRepository carRepository;
    ICarRentalRepository carRentalRepository;
    ICarProducerRepository carProducerRepository;

    public Car createCar(CarRequest req) {
        CarProducer producer = carProducerRepository.findById(req.getProducerId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCER_NOT_FOUND));

        Car car = Car.builder()
                .carName(req.getCarName())
                .carModelYear(req.getCarModelYear())
                .color(req.getColor())
                .capacity(req.getCapacity())
                .description(req.getDescription())
                .importDate(req.getImportDate())
                .producer(producer)
                .rentPrice(req.getRentPrice())
                .status(req.getStatus())
                .build();

        return carRepository.save(car);
    }

    public Car getCarById(String id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_FOUND));
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car updateCar(String id, CarRequest req) {
        Car car = getCarById(id);

        Optional<CarRental> latestRentalOpt = carRentalRepository.findTopByCar_CarIdOrderByPickupDateDesc(id);

        if (latestRentalOpt.isPresent()) {
            String latestStatus = latestRentalOpt.get().getStatus();

            if (List.of("PENDING", "CONFIRMED", "RENTED").contains(latestStatus)) {
                car.setColor(req.getColor());
                car.setCapacity(req.getCapacity());
                return carRepository.save(car);
            }
        }

        car.setCarName(req.getCarName());
        car.setCarModelYear(req.getCarModelYear());
        car.setColor(req.getColor());
        car.setCapacity(req.getCapacity());
        car.setDescription(req.getDescription());
        car.setImportDate(req.getImportDate());
        car.setRentPrice(req.getRentPrice());
        car.setStatus(req.getStatus());

        CarProducer producer = carProducerRepository.findById(req.getProducerId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCER_NOT_FOUND));
        car.setProducer(producer);

        return carRepository.save(car);
    }

    public String deleteCar(String id) {
        Car car = getCarById(id);

        Optional<CarRental> latestRentalOpt = carRentalRepository.findTopByCar_CarIdOrderByPickupDateDesc(id);

        if (latestRentalOpt.isPresent()) {
            String latestStatus = latestRentalOpt.get().getStatus();

            if (List.of("PENDING", "CONFIRMED", "RENTED").contains(latestStatus)) {
                throw new AppException(ErrorCode.CANNOT_DELETE_CAR_WITH_RENTAL);
            }
        }

        carRepository.delete(car);
        return String.format("Car %s deleted successfully.", id);
    }

    public String updateCarStatus(String id, String newStatus) {
        Car car = getCarById(id);
        car.setStatus(newStatus);
        carRepository.save(car);
        return String.format("Car %s status updated to %s.", id, newStatus);
    }
}

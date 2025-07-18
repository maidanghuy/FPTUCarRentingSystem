package com.example.FUCarRentingSystem.controller;

import com.example.FUCarRentingSystem.dto.request.CarRequest;
import com.example.FUCarRentingSystem.dto.response.APIResponse;
import com.example.FUCarRentingSystem.entity.Car;
import com.example.FUCarRentingSystem.service.CarService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    // Sample ID: 0949668a-c942-4b7b-9561-402ffbd1fd59
    @PostMapping
    public APIResponse<?> createCar(@RequestBody CarRequest request) {
        return APIResponse.<Car>builder()
                .result(carService.createCar(request))
                .build();
    }
    // Sample JSON
    /*
    {
          "carName": "Toyota Vios",
          "carModelYear": 2021,
          "color": "Red",
          "capacity": 5,
          "description": "Compact sedan",
          "importDate": "2022-01-01",
          "producerId": "e93a3c7f-5534-4b9e-93f7-497d9d9ed4d3",
          "rentPrice": 35.00,
          "status": "AVAILABLE"
    }
    */

    @GetMapping("/{id}")
    public APIResponse<?> getCar(@PathVariable String id) {
        return APIResponse.<Car>builder()
                .result(carService.getCarById(id))
                .build();
    }

    @GetMapping
    public APIResponse<?> getAllCars() {
        return APIResponse.<Iterable<Car>>builder()
                .result(carService.getAllCars())
                .build();
    }

    @PutMapping("/{id}")
    public APIResponse<?> updateCar(@PathVariable String id, @RequestBody CarRequest request) {
//        System.out.println("demo");
        return APIResponse.<Car>builder()
                .result(carService.updateCar(id, request))
                .build();
    }
    // Sample JSON
    /*
    {
          "carName": "Toyota Vios",
          "carModelYear": 2021,
          "color": "Red",
          "capacity": 5,
          "description": "Compact sedan",
          "importDate": "2022-01-01",
          "producerId": "e93a3c7f-5534-4b9e-93f7-497d9d9ed4d3",
          "rentPrice": 35.00,
          "status": "AVAILABLE"
    }
    */

    @DeleteMapping("/{id}")
    public APIResponse<?> deleteCar(@PathVariable String id) {
        return APIResponse.<String>builder()
                .result(carService.deleteCar(id))
                .build();
    }

    @PatchMapping("/{id}/status")
    public APIResponse<?> updateStatus(@PathVariable String id, @RequestBody JsonNode body) {
        String newStatus = body.get("status").asText();
        return APIResponse.<String>builder()
                .result(carService.updateCarStatus(id, newStatus))
                .build();
    }
    // Sample JSON
    /*
    {
          "status": "FULLSLOT"
    }
    */
}

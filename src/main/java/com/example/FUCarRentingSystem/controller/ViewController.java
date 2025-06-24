package com.example.FUCarRentingSystem.controller;

import ch.qos.logback.core.model.Model;
import com.example.FUCarRentingSystem.entity.Car;
import com.example.FUCarRentingSystem.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final CarService carService;

    @GetMapping("auth/login")
    public String loginPage() {
        return "auth/login"; // => /templates/auth/login.html
    }

    @GetMapping("auth/register")
    public String registerPage() {
        return "auth/register"; // => /templates/auth/register.html
    }

    @GetMapping("/")
    public String home() {
        return "home"; // =>
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // =>
    }

    @GetMapping("customer/profile")
    public String profilePage() {
        return "customer/profile"; // =>
    }

    @GetMapping("customer/rental")
    public String rentalsPage() {
        return "customer/rental"; // =>
    }

    @GetMapping("customer/rent-car")
    public String rentCar() {
        return "customer/rent_single"; // =>
    }

    @GetMapping("admin")
    public String adminPage() {
        return "dashboard/admin"; // =>
    }

    @GetMapping("/admin/cars")
    public String viewCars(Model model) {
        return "dashboard/cars"; // Trỏ tới file cars.html
    }

    @GetMapping("/admin/customers")
    public String newCar() {
        return "dashboard/customers"; // Trỏ tới file customer.html
    }

    @GetMapping("/admin/rentals")
    public String viewRentals() {
        return "dashboard/rentals"; // Trỏ tới file rentals.html
    }

    @GetMapping("/admin/producers")
    public String newCarPage() {
        return "dashboard/producers"; // Trỏ tới file new_car.html
    }
}

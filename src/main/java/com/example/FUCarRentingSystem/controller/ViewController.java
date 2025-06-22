package com.example.FUCarRentingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

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
}

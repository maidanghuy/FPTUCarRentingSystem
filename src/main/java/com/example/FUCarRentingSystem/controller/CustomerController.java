package com.example.FUCarRentingSystem.controller;

import com.example.FUCarRentingSystem.dto.response.APIResponse;
import com.example.FUCarRentingSystem.dto.response.AuthResponse;
import com.example.FUCarRentingSystem.dto.response.CustomerResponse;
import com.example.FUCarRentingSystem.entity.CarRental;
import com.example.FUCarRentingSystem.service.CarRentalService;
import com.example.FUCarRentingSystem.service.CustomerService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final CarRentalService carRentalService;

    @GetMapping("")
    public APIResponse<?> getAllCustomerProfile() {
        return APIResponse.<List<CustomerResponse>>builder()
                .result(customerService.getAllCustomers())
                .build();
    }

    @GetMapping("/search")
    public APIResponse<?> searchCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String identityCard,
            @RequestParam(required = false) String licenceNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return APIResponse.<Page<CustomerResponse>>builder()
                .result(customerService.searchCustomers(name, email, mobile, identityCard, licenceNumber,
                        PageRequest.of(page, size)))
                .build();
    }

    @PutMapping("/name")
    public APIResponse<?> updateName(@RequestHeader("Authorization") String token, @RequestBody JsonNode json) {
        customerService.updateCustomerName(token, json.get("name").asText());
        return APIResponse.<String>builder().result("Name updated!").build();
    }

    // Sample JSON
    /*
     * {
     * "name": "Nguyen Van B"
     * }
     */

    @PutMapping("/mobile")
    public APIResponse<?> updateMobile(@RequestHeader("Authorization") String token, @RequestBody JsonNode json) {
        customerService.updateCustomerMobile(token, json.get("mobile").asText());
        return APIResponse.<String>builder().result("Mobile updated!").build();
    }
    // Sample JSON
    /*
     * {
     * "mobile": "0912345678"
     * }
     */

    @PutMapping("/birthday")
    public APIResponse<?> updateBirthday(@RequestHeader("Authorization") String token, @RequestBody JsonNode json) {
        customerService.updateCustomerBirthday(token, LocalDateTime.parse(json.get("birthday").asText()));
        return APIResponse.<String>builder().result("Birthday updated!").build();
    }
    // Sample JSON
    /*
     * {
     * "birthday": "1998-06-21T00:00:00"
     * }
     */

    @PutMapping("/identity-card")
    public APIResponse<?> updateIdentityCard(@RequestHeader("Authorization") String token, @RequestBody JsonNode json) {
        customerService.updateCustomerIdentityCard(token, json.get("identity-card").asText());
        return APIResponse.<String>builder().result("Identity card updated!").build();
    }
    // Sample JSON
    /*
     * {
     * "identity-card": "987654321"
     * }
     */

    @PutMapping("/licence-number")
    public APIResponse<?> updateLicenceNumber(@RequestHeader("Authorization") String token,
            @RequestBody JsonNode json) {
        customerService.updateCustomerLicenceNumber(token, json.get("licence-number").asText());
        return APIResponse.<String>builder().result("Licence number updated!").build();
    }
    // Sample JSON
    /*
     * {
     * "licence-number": "A123456789"
     * }
     */

    @PutMapping("/licence-date")
    public APIResponse<?> updateLicenceDate(@RequestHeader("Authorization") String token, @RequestBody JsonNode json) {
        customerService.updateCustomerLicenceDate(token, LocalDateTime.parse(json.get("licence-date").asText()));
        return APIResponse.<String>builder().result("Licence date updated!").build();
    }
    // Sample JSON
    /*
     * {
     * "licence-date": "2021-01-01T00:00:00"
     * }
     */

    @PutMapping("/email")
    public APIResponse<?> updateEmail(@RequestHeader("Authorization") String token, @RequestBody JsonNode json) {
        customerService.updateCustomerEmail(token, json.get("email").asText());
        return APIResponse.<String>builder().result("Email updated!").build();
    }
    // Sample JSON
    /*
     * {
     * "email": "newemail@example.com"
     * }
     */

    @PutMapping("/password")
    public APIResponse<?> updatePassword(@RequestHeader("Authorization") String token, @RequestBody JsonNode json) {
        customerService.updateCustomerPassword(token, json.get("password").asText());
        return APIResponse.<String>builder().result("Password updated!").build();
    }

    // Sample JSON
    /*
     * {
     * "password": "NewSecurePassword123"
     * }
     */
    @PutMapping("/{customerId}")
    public APIResponse<?> updateCustomer(
            @RequestHeader("Authorization") String token,
            @PathVariable String customerId,
            @RequestBody com.example.FUCarRentingSystem.dto.request.CustomerRequest request) {
        customerService.updateCustomer(token, customerId, request);
        return APIResponse.<String>builder().result("Customer updated!").build();
    }

    @GetMapping("/rentals")
    public APIResponse<?> getCustomerRentals(@RequestHeader("Authorization") String token) {
        return APIResponse.<List<CarRental>>builder()
                .result(carRentalService.getRentalsByCustomerId(token))
                .build();
    }

    @GetMapping("/me")
    public APIResponse<?> getMyProfile(@RequestHeader("Authorization") String token) {
        return APIResponse.builder()
                .result(customerService.getMyProfile(token))
                .build();
    }

}

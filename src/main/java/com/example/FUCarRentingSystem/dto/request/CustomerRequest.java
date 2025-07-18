package com.example.FUCarRentingSystem.dto.request;

import lombok.Data;

@Data
public class CustomerRequest {
    private String name;
    private String email;
    private String mobile;
    private String birthday; // ISO string, ví dụ: 1998-06-21T00:00:00
    private String identityCard;
    private String licenceNumber;
    private String licenceDate; // ISO string
    private String password;
}
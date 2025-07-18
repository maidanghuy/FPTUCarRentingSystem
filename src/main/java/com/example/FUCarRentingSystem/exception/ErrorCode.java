package com.example.FUCarRentingSystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    // 9999 - System errors
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    // 1000–1099: Authentication & Authorization
    INVALID_KEY(1001, "Invalid API key", HttpStatus.BAD_REQUEST),

    // 1100–1199: Quản lý người dùng
    LICENCE_NUMBER_EXISTED(1101, "Licence number already exists", HttpStatus.BAD_REQUEST),
    MOBILE_EXISTED(1102, "Mobile number already exists", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(1104, "Email not found", HttpStatus.NOT_FOUND),
    IDENTITY_CARD_EXISTED(1103, "Identity card number already exists", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(1105, "Incorrect password", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1106, "Email already exists", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_FOUND(1107, "Customer not found", HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_FOUND(1108, "Account not found", HttpStatus.NOT_FOUND),
    // 1200–1299: Quản lý car
    PRODUCER_NOT_FOUND(1201, "Producer not found!", HttpStatus.NOT_FOUND),
    CAR_NOT_FOUND(1202, "Car not found!", HttpStatus.NOT_FOUND),
    CANNOT_DELETE_CAR_WITH_RENTAL(1203, "Cannot delete car with rental!", HttpStatus.BAD_REQUEST),
    INVALID_DATE_RANGE(1204, "Invalid date range!", HttpStatus.BAD_REQUEST),
    CONFLICT_CAR_RENTAL(1205, "Conflict car rental!", HttpStatus.BAD_REQUEST),
    // 1300–1399: Thanh toán
    RENTAL_NOT_FOUND(1301, "Rental not found!", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1302, "Unauthorized!", HttpStatus.UNAUTHORIZED),
    INVALID_RENTAL_STATUS(1303, "Invalid rental status!", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

}

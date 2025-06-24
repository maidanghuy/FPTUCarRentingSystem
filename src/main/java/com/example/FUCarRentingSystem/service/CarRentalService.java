package com.example.FUCarRentingSystem.service;

import com.example.FUCarRentingSystem.dto.request.CarRentalItemRequest;
import com.example.FUCarRentingSystem.dto.request.CreateCarRentalRequest;
import com.example.FUCarRentingSystem.dto.response.RentalReportResponse;
import com.example.FUCarRentingSystem.entity.Account;
import com.example.FUCarRentingSystem.entity.Car;
import com.example.FUCarRentingSystem.entity.CarRental;
import com.example.FUCarRentingSystem.entity.Customer;
import com.example.FUCarRentingSystem.exception.AppException;
import com.example.FUCarRentingSystem.exception.ErrorCode;
import com.example.FUCarRentingSystem.mapper.CarRentalMapper;
import com.example.FUCarRentingSystem.repository.IAccountRepository;
import com.example.FUCarRentingSystem.repository.ICarRentalRepository;
import com.example.FUCarRentingSystem.repository.ICarRepository;
import com.example.FUCarRentingSystem.repository.ICustomerRepository;
import com.example.FUCarRentingSystem.security.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarRentalService {
    ICarRentalRepository carRentalRepository;
    ICustomerRepository customerRepository;
    CarRentalMapper carRentalMapper;
    ICarRepository carRepository;
    IAccountRepository accountRepository;
    JwtUtil jwtUtil;

    public List<CarRental> getRentalsByCustomerId(String jwtToken) {
        Customer customer = getCustomerFromToken(jwtToken);
        String customerId = customer.getCustomerId();
        if (!customerRepository.existsById(customerId)) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        System.out.println(customerId);

        return carRentalRepository.findByCustomer_CustomerIdOrderByPickupDateDesc(customerId);
    }

    private Customer getCustomerFromToken(String jwtToken) {
        String token = jwtToken.replace("Bearer ", "");
        String accountId = jwtUtil.extractId(token);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        return customerRepository.findByAccount_AccountId(account.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
    }

    public List<RentalReportResponse> getRentalReportBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new AppException(ErrorCode.INVALID_DATE_RANGE);
        }
        // conver localdate -> localdatetime
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay().minusSeconds(1);
        List<CarRental> rentals = carRentalRepository
                .findByPickupDateBetweenOrderByPickupDateDesc(startDateTime, endDateTime);
        return carRentalMapper.toRentalReportList(rentals);
    }

    public Page<RentalReportResponse> getRentalReportBetweenPaged(LocalDate startDate, LocalDate endDate,
            Pageable pageable) {
        if (startDate.isAfter(endDate)) {
            throw new AppException(ErrorCode.INVALID_DATE_RANGE);
        }
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay().minusSeconds(1);
        Page<CarRental> rentalsPage = carRentalRepository.findByPickupDateBetweenOrderByPickupDateDesc(startDateTime,
                endDateTime, pageable);
        return rentalsPage.map(carRentalMapper::toRentalReport);
    }

    public List<CarRental> createRentalTransaction(String jwtToken, CreateCarRentalRequest request) {

        String token = jwtToken.replace("Bearer ", "");
        String accountId = jwtUtil.extractId(token);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Customer customer = customerRepository.findByAccount_AccountId(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        if (customer == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }

        List<CarRental> rentals = new ArrayList<>();

        for (CarRentalItemRequest item : request.getRentalItems()) {
            String carId = item.getCarId();
            LocalDateTime pickup = item.getPickupDate();
            LocalDateTime returns = item.getReturnDate();

            if (pickup.isAfter(returns)) {
                throw new AppException(ErrorCode.INVALID_DATE_RANGE);
            }

            Car car = carRepository.findById(carId)
                    .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_FOUND));

            boolean hasConflict = carRentalRepository
                    .existsByCar_CarIdAndPickupDateLessThanEqualAndReturnDateGreaterThanEqual(carId, returns, pickup);
            if (hasConflict) {
                throw new AppException(ErrorCode.CONFLICT_CAR_RENTAL);
            }

            CarRental rental = CarRental.builder()
                    .car(car)
                    .customer(customer)
                    .pickupDate(pickup)
                    .returnDate(returns)
                    .rentPrice(car.getRentPrice())
                    .status("PENDING")
                    .build();

            rentals.add(rental);
        }

        return carRentalRepository.saveAll(rentals);
    }

    public Page<RentalReportResponse> getAllRentalReportsPaged(Pageable pageable) {
        Page<CarRental> rentalsPage = carRentalRepository.findAll(pageable);
        return rentalsPage.map(carRentalMapper::toRentalReport);
    }

}

package com.example.FUCarRentingSystem.service;

import com.example.FUCarRentingSystem.dto.response.CustomerResponse;
import com.example.FUCarRentingSystem.entity.Account;
import com.example.FUCarRentingSystem.entity.Customer;
import com.example.FUCarRentingSystem.exception.AppException;
import com.example.FUCarRentingSystem.exception.ErrorCode;
import com.example.FUCarRentingSystem.repository.IAccountRepository;
import com.example.FUCarRentingSystem.repository.ICustomerRepository;
import com.example.FUCarRentingSystem.security.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService {

    ICustomerRepository customerRepository;
    IAccountRepository accountRepository;
    PasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;


    public void updateCustomerName(String jwtToken, String name) {
        Customer customer = getCustomerFromToken(jwtToken);
        customer.setCustomerName(name);
        customerRepository.save(customer);
    }

    public void updateCustomerMobile(String jwtToken, String mobile) {
        Customer customer = getCustomerFromToken(jwtToken);

        if (!customer.getMobile().equals(mobile) &&
                customerRepository.existsByMobile(mobile)) {
            throw new AppException(ErrorCode.MOBILE_EXISTED);
        }

        customer.setMobile(mobile);
        customerRepository.save(customer);
    }

    public void updateCustomerBirthday(String jwtToken, LocalDateTime birthday) {
        Customer customer = getCustomerFromToken(jwtToken);
        customer.setBirthday(birthday);
        customerRepository.save(customer);
    }

    public void updateCustomerIdentityCard(String jwtToken, String card) {
        Customer customer = getCustomerFromToken(jwtToken);

        if (!customer.getIdentityCard().equals(card) &&
                customerRepository.existsByIdentityCard(card)) {
            throw new AppException(ErrorCode.IDENTITY_CARD_EXISTED);
        }

        customer.setIdentityCard(card);
        customerRepository.save(customer);
    }

    public void updateCustomerLicenceNumber(String jwtToken, String licenceNumber) {
        Customer customer = getCustomerFromToken(jwtToken);

        if (!customer.getLicenceNumber().equals(licenceNumber) &&
                customerRepository.existsByLicenceNumber(licenceNumber)) {
            throw new AppException(ErrorCode.LICENCE_NUMBER_EXISTED);
        }

        customer.setLicenceNumber(licenceNumber);
        customerRepository.save(customer);
    }

    public void updateCustomerLicenceDate(String jwtToken, LocalDateTime licenceDate) {
        Customer customer = getCustomerFromToken(jwtToken);
        customer.setLicenceDate(licenceDate);
        customerRepository.save(customer);
    }

    public void updateCustomerEmail(String jwtToken, String email) {
        Customer customer = getCustomerFromToken(jwtToken);

        if (!customer.getEmail().equalsIgnoreCase(email) &&
                customerRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        customer.setEmail(email);
        customerRepository.save(customer);
    }

    public void updateCustomerPassword(String jwtToken, String rawPassword) {
        Customer customer = getCustomerFromToken(jwtToken);
        customer.setPassword(passwordEncoder.encode(rawPassword));
        customerRepository.save(customer);
    }

    private Customer getCustomerFromToken(String jwtToken) {
        String token = jwtToken.replace("Bearer ", "");
        String accountId = jwtUtil.extractId(token);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        return customerRepository.findByAccount_AccountId(account.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
    }

    public CustomerResponse getMyProfile(String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        String accountId = jwtUtil.extractId(token);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Customer customer = customerRepository.findByAccount_AccountId(account.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        return CustomerResponse.builder()
                .customerName(customer.getCustomerName())
                .mobile(customer.getMobile())
                .birthday(customer.getBirthday())
                .email(customer.getEmail())
                .identityCard(customer.getIdentityCard())
                .licenceNumber(customer.getLicenceNumber())
                .licenceDate(customer.getLicenceDate())
                .build();
    }
}

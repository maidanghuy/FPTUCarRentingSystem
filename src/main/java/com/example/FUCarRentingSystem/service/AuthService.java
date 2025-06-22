package com.example.FUCarRentingSystem.service;

import com.example.FUCarRentingSystem.dto.request.LoginRequest;
import com.example.FUCarRentingSystem.dto.request.RegisterRequest;
import com.example.FUCarRentingSystem.dto.response.AuthResponse;
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

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    IAccountRepository accountRepository;
    ICustomerRepository customerRepository;
    PasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;


    public AuthResponse register(RegisterRequest req) {
        // Kiểm tra nếu email đã tồn tại trong customer
        if (customerRepository.existsByEmail(req.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        // Tạo account (role mặc định là CUSTOMER)
        Account account = Account.builder()
                .accountName(req.getAccountName())
                .role("CUSTOMER") // role cố định
                .build();
        account = accountRepository.save(account);

        // Tạo customer gắn với account
        Customer customer = Customer.builder()
                .account(account)
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .customerName(req.getCustomerName())
                .mobile(req.getMobile())
                .birthday(req.getBirthday())
                .identityCard(req.getIdentityCard())
                .licenceNumber(req.getLicenceNumber())
                .licenceDate(req.getLicenceDate())
                .build();

        customerRepository.save(customer);

        // Tạo JWT
        String token = jwtUtil.generateToken(Map.of(
                "id", account.getAccountId(),
                "username", account.getAccountName(),
                "role", account.getRole()
        ));

        return new AuthResponse(token, account.getAccountName(), account.getRole());
    }


    public AuthResponse login(LoginRequest req) {
        Customer customer = customerRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));

        if (!passwordEncoder.matches(req.getPassword(), customer.getPassword())) {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }

        Account account = customer.getAccount();

        String token = jwtUtil.generateToken(Map.of(
                "id", account.getAccountId(),
                "username", account.getAccountName(),
                "role", account.getRole()
        ));

        return new AuthResponse(token, account.getAccountName(), account.getRole());
    }

}

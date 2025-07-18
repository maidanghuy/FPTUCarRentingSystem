package com.example.FUCarRentingSystem.service;

import com.example.FUCarRentingSystem.dto.response.CustomerResponse;
import com.example.FUCarRentingSystem.entity.Account;
import com.example.FUCarRentingSystem.entity.Customer;
import com.example.FUCarRentingSystem.exception.AppException;
import com.example.FUCarRentingSystem.exception.ErrorCode;
import com.example.FUCarRentingSystem.mapper.CustomerMapper;
import com.example.FUCarRentingSystem.repository.IAccountRepository;
import com.example.FUCarRentingSystem.repository.ICustomerRepository;
import com.example.FUCarRentingSystem.security.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService {

    ICustomerRepository customerRepository;
    IAccountRepository accountRepository;
    PasswordEncoder passwordEncoder;
    CustomerMapper customerMapper;
    JwtUtil jwtUtil;

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(customer -> CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .birthday(customer.getBirthday())
                .identityCard(customer.getIdentityCard())
                .licenceNumber(customer.getLicenceNumber())
                .licenceDate(customer.getLicenceDate())
                .build()).collect(Collectors.toList());
    }

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

    public void updateCustomer(String jwtToken, String customerId,
            com.example.FUCarRentingSystem.dto.request.CustomerRequest req) {
        // Chỉ cho phép admin hoặc chính user cập nhật
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        if (req.getName() != null)
            customer.setCustomerName(req.getName());
        if (req.getEmail() != null && !customer.getEmail().equalsIgnoreCase(req.getEmail())) {
            if (customerRepository.existsByEmail(req.getEmail())) {
                throw new AppException(ErrorCode.EMAIL_EXISTED);
            }
            customer.setEmail(req.getEmail());
        }
        if (req.getMobile() != null && !customer.getMobile().equals(req.getMobile())) {
            if (customerRepository.existsByMobile(req.getMobile())) {
                throw new AppException(ErrorCode.MOBILE_EXISTED);
            }
            customer.setMobile(req.getMobile());
        }
        if (req.getBirthday() != null) {
            customer.setBirthday(LocalDateTime.parse(req.getBirthday()));
        }
        if (req.getIdentityCard() != null && !customer.getIdentityCard().equals(req.getIdentityCard())) {
            if (customerRepository.existsByIdentityCard(req.getIdentityCard())) {
                throw new AppException(ErrorCode.IDENTITY_CARD_EXISTED);
            }
            customer.setIdentityCard(req.getIdentityCard());
        }
        if (req.getLicenceNumber() != null && !customer.getLicenceNumber().equals(req.getLicenceNumber())) {
            if (customerRepository.existsByLicenceNumber(req.getLicenceNumber())) {
                throw new AppException(ErrorCode.LICENCE_NUMBER_EXISTED);
            }
            customer.setLicenceNumber(req.getLicenceNumber());
        }
        if (req.getLicenceDate() != null) {
            customer.setLicenceDate(LocalDateTime.parse(req.getLicenceDate()));
        }
        if (req.getPassword() != null && !req.getPassword().isEmpty()) {
            customer.setPassword(passwordEncoder.encode(req.getPassword()));
        }
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

    public Page<CustomerResponse> searchCustomers(String name, String email, String mobile, String identityCard,
            String licenceNumber, Pageable pageable) {
        Specification<Customer> spec = Specification.where(null);
        if (name != null && !name.isEmpty()) {
            spec = spec.and(
                    (root, query, cb) -> cb.like(cb.lower(root.get("customerName")), "%" + name.toLowerCase() + "%"));
        }
        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }
        if (mobile != null && !mobile.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("mobile"), "%" + mobile + "%"));
        }
        if (identityCard != null && !identityCard.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("identityCard"), "%" + identityCard + "%"));
        }
        if (licenceNumber != null && !licenceNumber.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("licenceNumber"), "%" + licenceNumber + "%"));
        }
        Page<Customer> page = customerRepository.findAll(spec, pageable);
        return page.map(customer -> CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .birthday(customer.getBirthday())
                .identityCard(customer.getIdentityCard())
                .licenceNumber(customer.getLicenceNumber())
                .licenceDate(customer.getLicenceDate())
                .build());
    }
}

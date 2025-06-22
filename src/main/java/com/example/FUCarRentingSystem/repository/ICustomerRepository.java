package com.example.FUCarRentingSystem.repository;

import com.example.FUCarRentingSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByIdentityCard(String identityCard);
    boolean existsByMobile(String mobile);
    boolean existsByLicenceNumber(String licenceNumber);
    Optional<Customer> findByAccount_AccountId(String accountId);

}

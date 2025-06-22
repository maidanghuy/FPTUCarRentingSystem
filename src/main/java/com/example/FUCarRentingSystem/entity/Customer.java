package com.example.FUCarRentingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "customer", schema = "fucar_renting_system_db")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id", nullable = false, length = 50)
    String customerId;

    @Column(name = "customer_name", nullable = false, length = 100)
    String customerName;

    @Column(name = "mobile", nullable = false, length = 20)
    String mobile;

    @Column(name = "birthday", nullable = false)
    LocalDateTime birthday;

    @Column(name = "identity_card", nullable = false, length = 50)
    String identityCard;

    @Column(name = "licence_number", nullable = false, length = 50)
    String licenceNumber;

    @Column(name = "licence_date", nullable = false)
    LocalDateTime licenceDate;

    @Column(name = "email", nullable = false, length = 100)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    Account account;

}
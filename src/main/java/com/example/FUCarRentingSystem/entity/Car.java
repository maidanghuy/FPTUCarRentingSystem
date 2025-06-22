package com.example.FUCarRentingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "car", schema = "fucar_renting_system_db")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "car_id", nullable = false, length = 50)
    String carId;

    @Column(name = "car_name", nullable = false, length = 100)
    String carName;

    @Column(name = "car_model_year", nullable = false)
    Integer carModelYear;

    @Column(name = "color", nullable = false, length = 50)
    String color;

    @Column(name = "capacity", nullable = false)
    Integer capacity;

    @Lob
    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "import_date", nullable = false)
    LocalDateTime importDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producer_id", nullable = false)
    CarProducer producer;

    @Column(name = "rent_price", nullable = false, precision = 10, scale = 2)
    BigDecimal rentPrice;

    @Column(name = "status", nullable = false, length = 50)
    String status;

}
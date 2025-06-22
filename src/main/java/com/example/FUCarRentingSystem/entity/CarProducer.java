package com.example.FUCarRentingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "car_producer", schema = "fucar_renting_system_db")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CarProducer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "producer_id", nullable = false, length = 50)
    String producerId;

    @Column(name = "producer_name", nullable = false, length = 100)
    String producerName;

    @Column(name = "address", nullable = false)
    String address;

    @Column(name = "country", nullable = false, length = 100)
    String country;

}
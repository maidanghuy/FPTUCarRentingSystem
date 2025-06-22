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
@Table(name = "car_rental", schema = "fucar_renting_system_db")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CarRental {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rental_id", nullable = false, length = 50)
    String rentalId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    Car car;

    @Column(name = "pickup_date", nullable = false)
    LocalDateTime pickupDate;

    @Column(name = "return_date", nullable = false)
    LocalDateTime returnDate;

    @Column(name = "rent_price", nullable = false, precision = 10, scale = 2)
    BigDecimal rentPrice;

//    | Trạng thái  | Ý nghĩa thực tế                     |
//    | ----------- | ----------------------------------- |
//    | `PENDING`   | Đã tạo đơn thuê nhưng chưa xác nhận |
//    | `CONFIRMED` | Đơn thuê đã được xác nhận           |
//    | `RENTED`    | **Đang được thuê** – xe chưa trả về |
//    | `COMPLETED` | Thuê đã kết thúc                    |
//    | `CANCELLED` | Đơn bị huỷ                          |
    @Column(name = "status", nullable = false, length = 50)
    String status;

}
package com.kei.reservationservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    // 예약자 ID
    @Column(nullable = false, unique = true)
    private String userId;

    // 고객 이름
    @Column(nullable = false)
    private String name;

    // 고객 핸드폰 번호
    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private LocalDateTime reservationTime;
}

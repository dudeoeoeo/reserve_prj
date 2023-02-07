package com.kei.reservationservice.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyBookingRes {

    private Long id;
    private String status;
    private String userId;
    private String name;
    private String phone;
    private LocalDateTime reservationTime;

}

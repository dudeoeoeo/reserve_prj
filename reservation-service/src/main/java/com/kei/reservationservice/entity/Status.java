package com.kei.reservationservice.entity;

public enum Status {
    // 예약 가능, 예약중, 예약 취소, 예약 완료
    OPEN,
    IN_BOOKING,
    CANCEL,
    RESERVED
}

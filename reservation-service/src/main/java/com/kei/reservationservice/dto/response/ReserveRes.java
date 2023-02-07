package com.kei.reservationservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReserveRes {

    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime reservationTime;

    public ReserveRes(String status, LocalDateTime reservationTime) {
        this.status = status;
        this.reservationTime = reservationTime;
    }
}

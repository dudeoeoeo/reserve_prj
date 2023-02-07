package com.kei.reservationservice.controller;

import com.kei.reservationservice.dto.response.MyBookingRes;
import com.kei.reservationservice.dto.response.ReserveRes;
import com.kei.reservationservice.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReserveRes>> getReserveList(@RequestParam("date") String reqDate) {
        final List<ReserveRes> response = reservationService.getReserveList(reqDate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{userId}/booking")
    public ResponseEntity<List<MyBookingRes>> getMyBookingList(@PathVariable("userId") String userId) {
        final List<MyBookingRes> response = reservationService.getMyBookingList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

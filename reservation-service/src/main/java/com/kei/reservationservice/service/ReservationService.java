package com.kei.reservationservice.service;

import com.kei.reservationservice.dto.request.ReserveReq;
import com.kei.reservationservice.dto.response.MyBookingRes;
import com.kei.reservationservice.dto.response.ReserveRes;

import java.util.List;

public interface ReservationService {

    List<ReserveRes> getReserveList(String reqDate);
    List<MyBookingRes> getMyBookingList(String userId);
    void saveReserve(Long reserveId, ReserveReq req);

}

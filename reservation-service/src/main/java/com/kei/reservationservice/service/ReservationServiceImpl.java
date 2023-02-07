package com.kei.reservationservice.service;

import com.kei.reservationservice.dto.request.ReserveReq;
import com.kei.reservationservice.dto.response.MyBookingRes;
import com.kei.reservationservice.dto.response.ReserveRes;
import com.kei.reservationservice.entity.Reservation;
import com.kei.reservationservice.entity.ReservationRepository;
import com.kei.reservationservice.entity.Status;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private ModelMapper modelMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReserveRes> getReserveList(String reqDate) {
        return getDummyReserveList(reqDate);
    }

    @Override
    public List<MyBookingRes> getMyBookingList(String userId) {
        final List<Reservation> reservations = reservationRepository.findAllByUserId(userId);

        if (reservations.isEmpty()) {
            return new ArrayList<>();
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return reservations.stream().map(r -> modelMapper.map(r, MyBookingRes.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveReserve(Long reserveId, ReserveReq req) {

    }

    // 제휴된 업체의 예약 정보를 가져와서 비교
    // 현재는 가상 데이터 return
    private List<ReserveRes> getDummyReserveList(String reqData) {
        final LocalDate date = LocalDate.parse(reqData);
        List<ReserveRes> list = new ArrayList<>();
        for (int i = 9; i < 22; i++) {
            list.add(
                   new ReserveRes(Status.OPEN.toString(), date.atTime(i, 0))
            );
        }
        return list;
    }

}

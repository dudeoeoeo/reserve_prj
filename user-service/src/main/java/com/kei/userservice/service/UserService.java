package com.kei.userservice.service;

import com.kei.userservice.dto.UserDto;
import com.kei.userservice.vo.MyBookingRes;
import com.kei.userservice.vo.ReserveRes;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);
    UserDto getUserDetailByEmail(String email);
    List<MyBookingRes> getMyBookingList(String token);
    List<ReserveRes> getReserveList();
    String renewToken(String token);
}

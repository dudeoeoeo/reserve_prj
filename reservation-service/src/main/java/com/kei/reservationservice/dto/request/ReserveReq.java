package com.kei.reservationservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ReserveReq {

    @NotEmpty(message = "유저 아이디는 필수값 입니다.")
    private String userId;

    @NotEmpty(message = "이름은 필수값 입니다.")
    private String name;

    @NotEmpty(message = "핸드폰 번호는 필수값 입니다.")
    @Size(min = 10, message = "핸드폰 최소 10자 이상 입력해 주세요.")
    private String phone;

}

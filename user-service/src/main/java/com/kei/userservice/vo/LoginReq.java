package com.kei.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginReq {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
}

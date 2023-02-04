package com.kei.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserReq {
    @NotEmpty(message = "이름을 입력해주세요.")
    @Size(min = 2, message = "이름은 최소 2글자 이상 입력해주세요.")
    private String name;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Size(min = 2, message = "이메일을 최소 2글자 이상 입력해주세요.")
    @Email
    private String email;

    @NotEmpty(message = "전화번호를 입력해주세요.")
    @Size(min = 10, message = "전화번호는 최소 10글자 이상 입력해주세요.")
    private String phone;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "패스트워드는 최소 8자 이상 입력해주세요.")
    private String pwd;
}

package com.kei.userservice.dto;

import com.kei.userservice.entity.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private String email;
    private String name;
    private String phone;
    private String pwd;
    private String userId;
    private String encryptedPwd;
    private UserRole role;
}

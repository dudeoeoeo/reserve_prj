package com.kei.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRes {
    private String email;
    private String name;
    private String phone;
    private String userId;
}

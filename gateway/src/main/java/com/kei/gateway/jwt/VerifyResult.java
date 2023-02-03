package com.kei.gateway.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyResult {

    private String userId;
    private boolean result;
}

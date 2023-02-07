package com.kei.userservice.controller;

import com.kei.userservice.security.token.TokenProperty;

import javax.servlet.http.HttpServletRequest;

public class ControllerUtil {

    public String getToken(HttpServletRequest request) {
        final String token = request.getHeader(TokenProperty.HEADER_STRING);
        return token.replace(TokenProperty.HEADER_PREFIX, "");
    }
}

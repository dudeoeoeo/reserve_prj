package com.kei.userservice.controller;

import com.kei.userservice.dto.UserDto;
import com.kei.userservice.security.token.TokenProperty;
import com.kei.userservice.service.UserService;
import com.kei.userservice.vo.UserReq;
import com.kei.userservice.vo.UserRes;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class UserController {

    private UserService userService;
    private final ModelMapper mapper = new ModelMapper();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody UserReq req) {
        final UserDto userDto = mapper.map(req, UserDto.class);
        final UserDto user = userService.createUser(userDto);

        final UserRes userRes = mapper.map(user, UserRes.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRes);
    }

    @GetMapping("/renew")
    public ResponseEntity renewToken(HttpServletRequest request, HttpServletResponse response) {
        final String token = request.getHeader(TokenProperty.HEADER_STRING);
        final String renewToken = userService.renewToken(token);
        response.addHeader(TokenProperty.HEADER_STRING, TokenProperty.HEADER_PREFIX + renewToken);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}

package com.kei.userservice.controller;

import com.kei.userservice.dto.UserDto;
import com.kei.userservice.service.UserService;
import com.kei.userservice.vo.UserReq;
import com.kei.userservice.vo.UserRes;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

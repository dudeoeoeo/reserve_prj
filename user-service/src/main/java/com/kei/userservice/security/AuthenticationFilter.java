package com.kei.userservice.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kei.userservice.dto.UserDto;
import com.kei.userservice.entity.UserEntity;
import com.kei.userservice.security.token.TokenProperty;
import com.kei.userservice.security.token.TokenProvider;
import com.kei.userservice.service.UserService;
import com.kei.userservice.vo.LoginReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Environment env;
    private UserService userService;
    private Algorithm algorithm;
    private TokenProvider tokenProvider;
    private String secretKey;

    public AuthenticationFilter(AuthenticationManager authenticationManager, Environment env, UserService userService, TokenProvider tokenProvider) {
        super.setAuthenticationManager(authenticationManager);
        this.env = env;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.secretKey = env.getProperty("token.secret");
        this.algorithm = Algorithm.HMAC512(secretKey);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginReq req = new ObjectMapper().readValue(request.getInputStream(), LoginReq.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException
    {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        final UserDto userDetail = userService.getUserDetailByEmail(userName);

        final String accessToken = tokenProvider.generateToken(userDetail, secretKey, algorithm);

        response.addHeader(TokenProperty.HEADER_STRING, TokenProperty.HEADER_PREFIX + accessToken);
    }
}

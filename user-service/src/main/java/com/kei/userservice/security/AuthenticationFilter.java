package com.kei.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public AuthenticationFilter(AuthenticationManager authenticationManager, Environment env, UserService userService) {
        super.setAuthenticationManager(authenticationManager);
        this.env = env;
        this.userService = userService;
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
        String username = ((User) authResult.getPrincipal()).getUsername();
    }
}

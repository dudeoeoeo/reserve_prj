package com.kei.gateway.filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.kei.gateway.jwt.TokenVerify;
import com.kei.gateway.jwt.VerifyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private Environment env;
    private Algorithm algorithm;
    private TokenVerify tokenVerify = new TokenVerify();

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
        this.algorithm = Algorithm.HMAC512(env.getProperty("token.secret"));
    }

    @Override
    public GatewayFilter apply(AuthorizationHeaderFilter.Config config) {
        return (exchange, chain) -> {
            log.info("AuthorizationHeaderFilter start");
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Auth Header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            final VerifyResult verifyResult = tokenVerify.verify(authorizationHeader, algorithm);

            if (!verifyResult.isResult()) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            log.info("AuthorizationHeaderFilter response code => {}", response.getStatusCode());
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);

        log.error(errorMessage);
        return response.setComplete();
    }

    public static class Config {}
}

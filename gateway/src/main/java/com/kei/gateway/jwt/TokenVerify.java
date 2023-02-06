package com.kei.gateway.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenVerify {

    public VerifyResult verify(String token, Algorithm algorithm) {
        try {
            token = token.replace(TokenProperty.HEADER_PREFIX, "");
            DecodedJWT decoded = JWT.require(algorithm).build().verify(token);
            return VerifyResult.builder().userId(decoded.getClaim(TokenProperty.CLAIM_USER_ID).toString())
                    .result(true).build();
        } catch (JWTVerificationException e) {
            DecodedJWT decoded = JWT.decode(token);
            return VerifyResult.builder().userId(decoded.getClaim(TokenProperty.CLAIM_USER_ID).toString())
                    .result(false).build();
        }
    }
}

package com.wolroys.socialmediawebapp.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(String username){

        return JWT.create()
                .withIssuer("wolroys")
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600_0000))
                .withSubject("User details")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("wolroys")
                .build();

        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}

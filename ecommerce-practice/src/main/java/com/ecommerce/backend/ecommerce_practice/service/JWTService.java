package com.ecommerce.backend.ecommerce_practice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecommerce.backend.ecommerce_practice.model.LocalUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;
    private Algorithm algorithm;
    private static final String USERNAME_KEY = "USERNAME ";
    private static final String EMAIL_KEY = "EMAIL";
    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }
    public String generateJWT(LocalUser user) {
        return JWT.create().withClaim(USERNAME_KEY, user.getUsername())
                .withIssuer(issuer).
                withExpiresAt(new Date(System.currentTimeMillis() + expiryInSeconds * 1000))
                .sign(algorithm);
    }
    public String generateVerificationJWT(LocalUser user) {
        return JWT.create().withClaim(EMAIL_KEY, user.getEmail())
                .withIssuer(issuer).
                withExpiresAt(new Date(System.currentTimeMillis() + expiryInSeconds * 1000))
                .sign(algorithm);
    }
    public String getUsername(String token) {
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }
}

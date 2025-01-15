package com.dj.v_02.infra.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dj.v_02.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {
    @Value("${api.security.jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("auth0").withSubject(user.getUsername()).withClaim("id", user.getId()).withClaim("role", user.getRole().toString()).withExpiresAt(getExpiration()).sign(algorithm);
        } catch (JWTCreationException e) {
            return new ErrorResponse("Invalid secret").toString();
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            return new ErrorResponse("Token is required").toString();
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("auth0").build().verify(token).getSubject();
        } catch (Exception e) {
            return new ErrorResponse("Invalid token").toString();
        }
    }

    public boolean validateToken(String token) {
        if (token == null) {
            return false;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm).withIssuer("auth0").build().verify(token).getSubject();
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private Instant getExpiration() {
        return LocalDateTime.now().plusMinutes(60).toInstant(ZoneOffset.of("-05:00"));
    }

    private record ErrorResponse(String message) {
    }
}

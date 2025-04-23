package com.bacchiega.Restaurant.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bacchiega.Restaurant.entity.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenService {

    @Value("${restaurant.security.secret-key}")
    private String secret;

    public String generateToken(Client user) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId().toString())
                .withClaim("name", user.getName())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .withIssuer("API Restaurant")
                .sign(Algorithm.HMAC256(secret));
    }

    public Optional<JWTUserData> verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT verify = JWT.require(algorithm).build().verify(token);

            return Optional.of(JWTUserData
                    .builder()
                    .id(UUID.fromString(verify.getClaim("userId").asString()))
                    .name(verify.getClaim("name").asString())
                    .email(verify.getSubject())
                    .build()
            );
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}

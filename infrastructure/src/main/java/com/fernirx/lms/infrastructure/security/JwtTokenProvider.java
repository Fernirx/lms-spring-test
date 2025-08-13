package com.fernirx.lms.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private Key key;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {
        validateJwtSecret();

        key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(
                        jwtProperties.getSecret()
                )
        );
    }

    private void validateJwtSecret() {
        String secret = jwtProperties.getSecret();
        if (secret == null || secret.trim().isEmpty())
            throw new IllegalStateException("JWT secret is not configured!");

        try {
            Decoders.BASE64.decode(secret);
        }  catch (Exception e) {
            log.warn("JWT secret is not base64 encoded. Encoding it now...");
            String encoded = Base64.getEncoder()
                    .encodeToString(secret.getBytes());
            jwtProperties.setSecret(encoded);
        }
    }

    public String generateToken(Authentication authentication) {
        CustomUserDetails  userDetails = (CustomUserDetails) authentication.getPrincipal();

        Map<String,Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("authorities", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return createToken(claims, userDetails.getUsername(),
                jwtProperties.getExpiration());
    }

    private String createToken(Map<String,Object> claims, String subject, long expiration) {
        Date now = new Date();
        Date expirationDate = new Date(expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expirationDate)
                .issuer(jwtProperties.getIssuer())
                .signWith(key)
                .compact();
    }
}

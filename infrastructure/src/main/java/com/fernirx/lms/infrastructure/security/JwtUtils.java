package com.fernirx.lms.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Slf4j
@Component
public class JwtUtils {

    private final JwtProperties jwtProperties;
    private SecretKey key;

    public JwtUtils(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(
                        jwtProperties.getSecret()
                )
        );
    }

    public String generateAccessToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Map<String, Object> claims = createClaims(userDetails);
        claims.put("type", "access_token");

        return createToken(claims, userDetails.getUsername(), jwtProperties.getExpiration());
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh_token");
        claims.put("username", username);

        return createToken(claims, username, jwtProperties.getRefreshExpiration());
    }

    private Map<String, Object> createClaims(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("authorities", SecurityUtils.getAuthorities(userDetails));
        return claims;
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

    public boolean validateAccessToken(String token) {
        return validateToken(token, "access_token");
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, "refresh_token");
    }

    public boolean validateToken(String token, String expectedType) {
        try {
            Claims claims = extractAllClaims(token);
            String tokenType = claims.get("type").toString();

            if (!expectedType.equals(tokenType)) {
                log.error("Invalid token type. Expected {} but got {}", expectedType, tokenType);
                throw new IllegalArgumentException("Invalid JWT token type");
            }

            return true;
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException |
                 SecurityException | IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected JWT validation error", e);
            throw new IllegalArgumentException("JWT validation failed", e);
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).get("username").toString();
    }

    public List<String> getAuthoritiesFromToken(String token) {
        Object authorities = extractAllClaims(token).get("authorities");
        if (authorities instanceof List<?>) {
            return ((List<?>) authorities).stream()
                    .map(Object::toString)
                    .toList();
        }
        return List.of();
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractAllClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}

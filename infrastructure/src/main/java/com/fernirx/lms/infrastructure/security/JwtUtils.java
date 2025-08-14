package com.fernirx.lms.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenUtils {

    private final JwtProperties jwtProperties;
    private SecretKey key;

    public JwtTokenUtils(JwtProperties jwtProperties) {
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

    public String generateAccessToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Map<String, Object> claims = createAccessClaims(userDetails);

        return createToken(claims, userDetails.getUsername(), jwtProperties.getExpiration());
    }

    public String generateAccessToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = createAccessClaims(userDetails);

        return createToken(claims, userDetails.getUsername(), jwtProperties.getExpiration());
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh_token");
        claims.put("username", username);

        return createToken(claims, username, jwtProperties.getRefreshExpiration());
    }

    private Map<String, Object> createAccessClaims(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access_token");
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
        try {
            Claims claims = extractAllClaims(token);
            String type = claims.get("type").toString();

            if (!type.equals("access_token")){
                log.error("Invalid token type. Expected access_token but got {}", type);
                return false;
            }

            return true;

        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

package com.fernirx.lms.infrastructure.security;

import com.fernirx.lms.common.constants.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

        return createToken(
                SecurityConstants.JWT_ACCESS_TOKEN,
                userDetails.getId(),
                userDetails.getUsername(),
                SecurityUtils.getAuthorities(userDetails),
                jwtProperties.getExpiration()
        );
    }

    public String generateRefreshToken(int userId, String username) {
        return createToken(
                SecurityConstants.JWT_REFRESH_TOKEN,
                userId,
                username,
                null,
                jwtProperties.getRefreshExpiration()
        );
    }

    public String refreshAccessToken(String accessToken, String refreshToken) {
        validateRefreshToken(refreshToken);
        int userId = Integer.parseInt(extractSubject(refreshToken));
        String username = extractUsername(refreshToken);
        List<String> authorities = extractAuthoritiesIgnoreExpiry(accessToken);
        return createToken(
                SecurityConstants.JWT_ACCESS_TOKEN,
                userId,
                username,
                authorities,
                jwtProperties.getExpiration()
        );
    }

    public String rotateRefreshToken(String oldRefreshToken) {
        validateRefreshToken(oldRefreshToken);
        int userId = Integer.parseInt(extractSubject(oldRefreshToken));
        String username = extractUsername(oldRefreshToken);
        return generateRefreshToken(userId, username);
    }

    private String createToken(String type, int userId, String username, List<String> authorities, long expirationMillis) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstants.JWT_CLAIMS_TYPE, type);
        claims.put(SecurityConstants.JWT_CLAIMS_USERNAME, username);
        if (authorities != null) {
            claims.put(SecurityConstants.JWT_CLAIMS_AUTHORITIES, authorities);
        }
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(userId)) // sub = id
                .issuedAt(now)
                .expiration(expirationDate)
                .issuer(jwtProperties.getIssuer())
                .signWith(key)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, SecurityConstants.JWT_ACCESS_TOKEN);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, SecurityConstants.JWT_REFRESH_TOKEN);
    }

    public boolean validateToken(String token, String expectedType) {
        try {
            Claims claims = extractAllClaims(token);
            String tokenType = claims.get(SecurityConstants.JWT_CLAIMS_TYPE).toString();

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

    private Claims extractAllClaimsIgnoreExpiry(String token) {
        try {
            return extractAllClaims(token);
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractUsername(String token) {
        Object username = extractAllClaims(token).get(SecurityConstants.JWT_CLAIMS_USERNAME);
        return username != null ? username.toString() : null;
    }

    public List<String> extractAuthorities(String token) {
        Object authorities = extractAllClaims(token).get(SecurityConstants.JWT_CLAIMS_AUTHORITIES);
        if (authorities instanceof List<?>) {
            return ((List<?>) authorities).stream()
                    .map(Object::toString)
                    .toList();
        }
        return List.of();
    }

    public List<String> extractAuthoritiesIgnoreExpiry(String token) {
        Object authorities = extractAllClaimsIgnoreExpiry(token).get(SecurityConstants.JWT_CLAIMS_AUTHORITIES);
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

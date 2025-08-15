package com.fernirx.lms.application;

import com.fernirx.lms.infrastructure.security.CustomUserDetails;
import com.fernirx.lms.infrastructure.security.JwtProperties;
import com.fernirx.lms.infrastructure.security.JwtUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtUtilsTest {

    private Authentication authentication;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void contextLoads() {
        assertNotNull(jwtUtils);
    }

    @BeforeEach
    void beforeEach() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        CustomUserDetails userDetails =
                new CustomUserDetails(1, "user", "password", List.of(grantedAuthority));
        authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Test
    void testGenerateAndValidateAccessToken() {
        String accessToken = jwtUtils.generateAccessToken(authentication);
        System.out.println("Access Token: " + accessToken);

        assertNotNull(accessToken);
        assertTrue(jwtUtils.validateAccessToken(accessToken));
        assertEquals("user", jwtUtils.extractUsername(accessToken));
        assertTrue(jwtUtils.extractAuthorities(accessToken).contains("ROLE_USER"));
    }

    @Test
    void testGenerateAndValidateRefreshToken() {
        String refreshToken = jwtUtils.generateRefreshToken("user");
        System.out.println("Refresh Token: " + refreshToken);

        assertNotNull(refreshToken);
        assertTrue(jwtUtils.validateRefreshToken(refreshToken));
        assertEquals("user", jwtUtils.extractUsername(refreshToken));
    }

    @Test
    void testRefreshAccessToken() throws InterruptedException {
        String oldAccessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken("user");
        Thread.sleep(1000);
        String newAccessToken = jwtUtils.refreshAccessToken(oldAccessToken, refreshToken);
        System.out.println("Old Access Token: " + oldAccessToken);
        System.out.println("Refresh Token: " + refreshToken);
        System.out.println("New Access Token: " + newAccessToken);

        assertNotNull(newAccessToken);
        assertTrue(jwtUtils.validateAccessToken(newAccessToken));
        assertEquals("user", jwtUtils.extractUsername(newAccessToken));
        assertTrue(jwtUtils.extractAuthorities(newAccessToken).contains("ROLE_USER"));
        assertNotEquals(oldAccessToken, newAccessToken);
    }

    @Test
    void testRotateRefreshToken() {
        String refreshToken = jwtUtils.generateRefreshToken("user");
        String rotateRefreshToken = jwtUtils.rotateRefreshToken(refreshToken);
        System.out.println("Refresh Token: " + refreshToken);
        System.out.println("Rotate Refresh Token: " + rotateRefreshToken);

        assertNotNull(rotateRefreshToken);
        assertEquals(refreshToken, rotateRefreshToken);
        assertEquals("user", jwtUtils.extractUsername(rotateRefreshToken));
        assertTrue(jwtUtils.validateRefreshToken(rotateRefreshToken));
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        String accessToken = jwtUtils.generateAccessToken(authentication);
        Thread.sleep(10000);
        assertTrue(jwtUtils.isTokenExpired(accessToken));
    }
}

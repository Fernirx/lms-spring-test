package com.fernirx.lms.infrastructure.security;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

    private String secret;

    private long expiration;

    private long refreshExpiration;

    private String issuer;
}

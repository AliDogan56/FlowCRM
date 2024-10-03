package com.dogan.bilisim.notificationservice.auth.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "flowcrm.security.jwt")
@Getter
@Setter
public class JwtSettings {
    private Integer tokenExpirationTime;
    private String tokenIssuer;
    private File jwtPrivateFile;
    private File jwtPublicFile;
}
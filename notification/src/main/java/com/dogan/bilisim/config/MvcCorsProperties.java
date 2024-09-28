package com.dogan.bilisim.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.HashMap;
import java.util.Map;


@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.mvc.cors")
public class MvcCorsProperties {

    private Map<String, CorsConfiguration> mappings = new HashMap<>();

    public MvcCorsProperties() {
        super();
    }

    public void setMappings(Map<String, CorsConfiguration> mappings) {
        this.mappings = mappings;
    }
}

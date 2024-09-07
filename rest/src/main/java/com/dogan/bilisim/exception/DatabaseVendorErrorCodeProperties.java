package com.dogan.bilisim.exception;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Getter
@Configuration
@ConfigurationProperties(prefix = "database.vendor.error")
public class DatabaseVendorErrorCodeProperties {

    private Map<Integer, DatabaseErrorTranslation> codes = new HashMap<>();

    public DatabaseVendorErrorCodeProperties() {
        super();
    }

    public void setCodes(Map<Integer, DatabaseErrorTranslation> codes) {
        this.codes = codes;
    }
}

package com.dogan.bilisim.restservice.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenExtractor {
    String extract(HttpServletRequest request);
}
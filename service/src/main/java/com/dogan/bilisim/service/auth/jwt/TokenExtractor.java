package com.dogan.bilisim.service.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenExtractor {
    String extract(HttpServletRequest request);
}
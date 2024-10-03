package com.dogan.bilisim.notificationservice.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenExtractor {
    String extract(HttpServletRequest request);
}
package com.dogan.bilisim.domain.auth.token;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AccessTokenType {
    BEARER("Bearer");
    private static final Map<String, AccessTokenType> lookup;
    private final String value;
    
    static {
        lookup = new HashMap<>();
        for (AccessTokenType eventType : EnumSet.allOf(AccessTokenType.class)) {
            lookup.put(eventType.value(), eventType);
        }
    }

    AccessTokenType(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static AccessTokenType convert(final String value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("ApiMessages.NullIsNotAValidValue");
        }
        return lookup.get(value);
    }
}

package com.dogan.bilisim.domain.exception;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FlowCrmErrorCode {

    MISCONFIGURATION_ERROR_CODE(1),
    GENERAL(100),
    AUTHENTICATION(101),
    JWT_TOKEN_EXPIRED(102),
    PERMISSION_DENIED(103),
    BAD_REQUEST_PARAMS(104),
    INVALID_ARGUMENTS(105),
    ITEM_NOT_FOUND(106),
    INVALID_HEADER_VALUE(107),
    UNSUPPORTED_LOCALE(108),
    MAP_API_EXCEPTION(109),
    UNIQUE_CONSTRAINS(110),
    BAD_HEADER_PARAMS(111),
    OUT_OF_BOUNDS_PARAMS(112),
    UNKNOWN_ERROR_CODE(999),
    SALT_PROBLEM(113);

    private static final Map<Integer, FlowCrmErrorCode> lookup;

    static {
        lookup = new HashMap<>();
        for (FlowCrmErrorCode flowCrmErrorCode : EnumSet.allOf(FlowCrmErrorCode.class)) {
            lookup.put(flowCrmErrorCode.getErrorCode(), flowCrmErrorCode);
        }
    }

    private final Integer errorCode;

    FlowCrmErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public static FlowCrmErrorCode convert(final Integer value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("ApiMessages.NullIsNotAValidValue");
        }
        return lookup.get(value);
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }

}

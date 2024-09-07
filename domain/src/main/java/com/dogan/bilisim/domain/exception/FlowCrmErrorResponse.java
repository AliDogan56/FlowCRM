package com.dogan.bilisim.domain.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
public class FlowCrmErrorResponse {
    // HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    // Error code
    private final FlowCrmErrorCode errorCode;

    private final ZonedDateTime timestamp;

    private String details;

    protected FlowCrmErrorResponse(final String message, final FlowCrmErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = ZonedDateTime.now();
    }

    public static FlowCrmErrorResponse of(final String message, final FlowCrmErrorCode errorCode, HttpStatus status) {
        return new FlowCrmErrorResponse(message, errorCode, status);
    }

    public Integer getStatus() {
        return status.value();
    }

}

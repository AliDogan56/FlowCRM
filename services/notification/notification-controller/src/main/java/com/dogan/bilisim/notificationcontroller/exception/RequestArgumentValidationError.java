package com.dogan.bilisim.notificationcontroller.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestArgumentValidationError {
    private String field;
    private Object rejectValue;
    private String sourceObject;
    private String errorMessage;
}

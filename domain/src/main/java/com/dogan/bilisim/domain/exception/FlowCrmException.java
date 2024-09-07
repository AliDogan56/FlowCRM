package com.dogan.bilisim.domain.exception;

public interface FlowCrmException {
    FlowCrmErrorCode getErrorCode();

    String getMessage();
}

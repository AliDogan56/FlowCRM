package com.dogan.bilisim.restservice.auth.exception;

import com.dogan.bilisim.domain.exception.FlowCrmErrorCode;
import com.dogan.bilisim.domain.exception.FlowCrmException;
import org.springframework.security.authentication.AuthenticationServiceException;

public class FlowCrmSecurityException extends AuthenticationServiceException implements FlowCrmException {
    private final FlowCrmErrorCode flowCrmErrorCode;

    public FlowCrmSecurityException(FlowCrmErrorCode flowCrmErrorCode, final String message) {
        super(message);
        this.flowCrmErrorCode = flowCrmErrorCode;
    }

    public FlowCrmSecurityException(FlowCrmErrorCode flowCrmErrorCode, final String message, final Throwable cause) {
        super(message, cause);
        this.flowCrmErrorCode = flowCrmErrorCode;
    }

    @Override
    public FlowCrmErrorCode getErrorCode() {
        return this.flowCrmErrorCode;
    }

}

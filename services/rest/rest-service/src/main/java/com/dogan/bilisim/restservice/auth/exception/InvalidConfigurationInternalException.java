package com.dogan.bilisim.restservice.auth.exception;


import com.dogan.bilisim.domain.exception.AbstractInternalException;
import com.dogan.bilisim.domain.exception.FlowCrmErrorCode;

public class InvalidConfigurationInternalException extends AbstractInternalException {
    public InvalidConfigurationInternalException(Throwable e) {
        super(e);
    }

    @Override
    public FlowCrmErrorCode getErrorCode() {
        return FlowCrmErrorCode.MISCONFIGURATION_ERROR_CODE;
    }
}

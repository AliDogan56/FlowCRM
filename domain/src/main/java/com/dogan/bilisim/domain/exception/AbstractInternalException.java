package com.dogan.bilisim.domain.exception;

public abstract class AbstractInternalException extends AbstractFlowCrmException {
    protected AbstractInternalException(String message) {
        super(message);
    }

    protected AbstractInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AbstractInternalException(Throwable cause) {
        super(cause);
    }

    protected AbstractInternalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

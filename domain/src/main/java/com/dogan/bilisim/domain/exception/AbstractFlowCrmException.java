package com.dogan.bilisim.domain.exception;

public abstract class AbstractFlowCrmException extends RuntimeException implements FlowCrmException {

    protected AbstractFlowCrmException(final String message) {
        super(message);
    }

    protected AbstractFlowCrmException(final String message, final Throwable cause) {
        super(message, cause);
    }

    protected AbstractFlowCrmException(final Throwable cause) {
        super(cause);
    }

    protected AbstractFlowCrmException(
            final String message,
            final Throwable cause,
            final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

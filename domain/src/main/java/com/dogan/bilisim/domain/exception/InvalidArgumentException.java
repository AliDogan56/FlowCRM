package com.dogan.bilisim.domain.exception;

public class InvalidArgumentException extends AbstractFlowCrmException {
    private final FlowCrmErrorCode flowCrmErrorCode;

    public InvalidArgumentException(final String message) {
        super(message);
        this.flowCrmErrorCode = FlowCrmErrorCode.INVALID_ARGUMENTS;
    }

    public InvalidArgumentException(final String message, final Throwable cause) {
        super(message, cause);
        this.flowCrmErrorCode = FlowCrmErrorCode.INVALID_ARGUMENTS;
    }

    @Override
    public FlowCrmErrorCode getErrorCode() {
        return this.flowCrmErrorCode;
    }

}

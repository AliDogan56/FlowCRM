package com.dogan.bilisim.notificationservice.auth.exception;


import com.dogan.bilisim.domain.exception.AbstractFlowCrmException;
import com.dogan.bilisim.domain.exception.FlowCrmErrorCode;

public class FlowCrmRestException extends AbstractFlowCrmException {
    private final FlowCrmErrorCode flowCrmErrorCode;

    public FlowCrmRestException(final String message, final FlowCrmErrorCode flowCrmErrorCode) {
        super(message);
        this.flowCrmErrorCode = flowCrmErrorCode;
    }

    public FlowCrmRestException(String message, Throwable cause, FlowCrmErrorCode flowCrmErrorCode) {
        super(message, cause);
        this.flowCrmErrorCode = flowCrmErrorCode;
    }

    @Override
    public FlowCrmErrorCode getErrorCode() {
        return this.flowCrmErrorCode;
    }

}

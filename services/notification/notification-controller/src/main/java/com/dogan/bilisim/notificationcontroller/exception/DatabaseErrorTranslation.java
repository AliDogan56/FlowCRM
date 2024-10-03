package com.dogan.bilisim.notificationcontroller.exception;

import com.dogan.bilisim.domain.exception.FlowCrmErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseErrorTranslation {
    private String message;
    private FlowCrmErrorCode errorCode;
}

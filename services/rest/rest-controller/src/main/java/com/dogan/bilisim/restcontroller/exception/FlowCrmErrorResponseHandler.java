
package com.dogan.bilisim.restcontroller.exception;


import com.dogan.bilisim.domain.exception.AbstractFlowCrmException;
import com.dogan.bilisim.domain.exception.FlowCrmErrorCode;
import com.dogan.bilisim.domain.exception.FlowCrmErrorResponse;
import com.dogan.bilisim.domain.exception.JwtExpiredTokenException;
import com.dogan.bilisim.domain.exception.RequestArgumentValidationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class FlowCrmErrorResponseHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private DatabaseVendorErrorCodeProperties databaseVendorErrorCodeProperties;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        if (!response.isCommitted()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            mapper.writeValue(response.getWriter(),
                    FlowCrmErrorResponse.of("Auth.unauthorized.access",
                            FlowCrmErrorCode.PERMISSION_DENIED, HttpStatus.FORBIDDEN));
        }
    }

    public void handle(Throwable exception, HttpServletResponse response) {
        log.error(exception.getMessage(), exception);
        if (!response.isCommitted()) {
            try {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
                if (exception instanceof AbstractFlowCrmException) {
                    handleFlowCrmException((AbstractFlowCrmException) exception, response);
                } else if (exception instanceof AccessDeniedException) {
                    handleAccessDeniedException(response);
                } else if (exception instanceof AuthenticationException) {
                    handleAuthenticationException((AuthenticationException) exception, response);
                } else if (exception instanceof MethodArgumentNotValidException) {
                    handleValidationException((MethodArgumentNotValidException) exception, response);
                } else if (exception instanceof SQLException) {
                    handleDatabaseException((SQLException) exception, response);
                } else if (exception instanceof DataIntegrityViolationException) {
                    handleDataIntegrityViolationException((DataIntegrityViolationException) exception, response);
                } else if (exception instanceof PropertyReferenceException) {
                    handleInvalidMappingException((PropertyReferenceException) exception, response);
                } else if (exception instanceof QueryException) {
                    handleInvalidQueryException((QueryException) exception, response);
                } else if (exception instanceof DataAccessException) {
                    handleDataAccessException((DataAccessException) exception, response);
                } else if (exception instanceof JwtException) {
                    handleJwtExceptionException((JwtException) exception, response);
                } else {
                    handleGeneralException(exception, response);
                }
            } catch (IOException e) {
                log.error("Can't handle exception", e);
            }
        }
    }

    private void handleDataIntegrityViolationException(DataIntegrityViolationException exception, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        FlowCrmErrorResponse errorResponse = FlowCrmErrorResponse.of(
                Objects.requireNonNull(exception.getRootCause()).getLocalizedMessage(), FlowCrmErrorCode.BAD_REQUEST_PARAMS, HttpStatus.BAD_REQUEST);
        String stackTraceStr = getStringOfStackTrace(exception.getRootCause());
        errorResponse.setDetails(stackTraceStr);
        mapper.writeValue(response.getWriter(), errorResponse);
    }

    private void handleGeneralException(Throwable exception, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String stackTraceStr = getStringOfStackTrace(exception);
        FlowCrmErrorResponse errorResponse = FlowCrmErrorResponse.of(exception.getMessage(),
                FlowCrmErrorCode.GENERAL, HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setDetails(stackTraceStr);
        mapper.writeValue(response.getWriter(), errorResponse);
    }

    private String getStringOfStackTrace(Throwable exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(exception.toString()).append("\n");
        Arrays.stream(exception.getStackTrace()).limit(15).forEach(stack -> {
            stringBuilder.append(stack);
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

    private void handleDataAccessException(DataAccessException exception, HttpServletResponse response) throws IOException {
        if (exception.getRootCause() instanceof QueryException) {
            handleInvalidQueryException((QueryException) exception.getRootCause(), response);
        } else {
            handleGeneralException(exception, response);
        }
    }

    private void handleJwtExceptionException(JwtException exception, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        if (exception instanceof MalformedJwtException) {
            mapper.writeValue(response.getWriter(), FlowCrmErrorResponse.of(
                    mapper.writeValueAsString(Collections.singletonList(
                            RequestArgumentValidationError.builder().errorMessage("Validation.header.jwt.malformed").build())), FlowCrmErrorCode.BAD_HEADER_PARAMS, HttpStatus.UNAUTHORIZED));
        } else if (exception instanceof ExpiredJwtException) {
            mapper.writeValue(response.getWriter(), FlowCrmErrorResponse.of(
                    mapper.writeValueAsString(Collections.singletonList(
                            RequestArgumentValidationError.builder().errorMessage("Validation.header.jwt.expired").build())), FlowCrmErrorCode.BAD_HEADER_PARAMS, HttpStatus.UNAUTHORIZED));
        } else {
            mapper.writeValue(response.getWriter(), FlowCrmErrorResponse.of(
                    mapper.writeValueAsString(Collections.singletonList(
                            RequestArgumentValidationError.builder().errorMessage("Validation.header.jwt.invalid").build())), FlowCrmErrorCode.BAD_HEADER_PARAMS, HttpStatus.UNAUTHORIZED));
        }
    }

    private void handleInvalidQueryException(QueryException exception, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        FlowCrmErrorResponse errorResponse = FlowCrmErrorResponse.of(
                mapper.writeValueAsString(getMappingErrors(exception)), FlowCrmErrorCode.BAD_REQUEST_PARAMS, HttpStatus.BAD_REQUEST);
        String stackTraceStr = getStringOfStackTrace(exception);
        errorResponse.setDetails(stackTraceStr);
        mapper.writeValue(response.getWriter(), errorResponse);
    }

    private void handleInvalidMappingException(PropertyReferenceException exception, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        mapper.writeValue(response.getWriter(), FlowCrmErrorResponse.of(
                mapper.writeValueAsString(getMappingErrors(exception)), FlowCrmErrorCode.BAD_REQUEST_PARAMS, HttpStatus.BAD_REQUEST));
    }

    private void handleValidationException(MethodArgumentNotValidException exception, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        mapper.writeValue(response.getWriter(), FlowCrmErrorResponse.of(
                getValidationErrors(exception), FlowCrmErrorCode.BAD_REQUEST_PARAMS, HttpStatus.BAD_REQUEST));
    }

    private String getValidationErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream().map(error ->
                (FieldError.class.isAssignableFrom(error.getClass()) ? ((FieldError) error).getField() : null) + " " + error.getDefaultMessage()
        ).toList().get(0);
    }


    private List<RequestArgumentValidationError> getMappingErrors(PropertyReferenceException exception) {
        return Collections.singletonList(
                RequestArgumentValidationError.builder().errorMessage("Validation.invalid.property.name")
                        .sourceObject(Objects.requireNonNull(exception.getType().getActualType()).getType().getSimpleName())
                        .field(exception.getPropertyName()).build());
    }


    private List<RequestArgumentValidationError> getMappingErrors(QueryException exception) {
        return Collections.singletonList(
                RequestArgumentValidationError.builder().errorMessage("Validation.invalid.property.name")
                        .sourceObject(exception.getQueryString())
                        .field(exception.getQueryString())
                        .build());
    }


    private void handleFlowCrmException(AbstractFlowCrmException flowCrmException, HttpServletResponse response) throws IOException {

        FlowCrmErrorCode errorCode = flowCrmException.getErrorCode();
        HttpStatus status;
        String message = flowCrmException.getMessage();

        status = switch (errorCode) {
            case AUTHENTICATION -> HttpStatus.UNAUTHORIZED;
            case PERMISSION_DENIED -> HttpStatus.FORBIDDEN;
            case INVALID_ARGUMENTS, INVALID_HEADER_VALUE, BAD_REQUEST_PARAMS -> HttpStatus.BAD_REQUEST;
            case ITEM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        response.setStatus(status.value());
        FlowCrmErrorResponse errorResponse = FlowCrmErrorResponse.of(message, errorCode, status);

        if (flowCrmException.getCause() != null)
            errorResponse.setDetails(getStringOfStackTrace(flowCrmException.getCause()));
        mapper.writeValue(response.getWriter(), errorResponse);
    }


    private void handleAccessDeniedException(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        mapper.writeValue(response.getWriter(),
                FlowCrmErrorResponse.of("You don't have permission to perform this operation!",
                        FlowCrmErrorCode.PERMISSION_DENIED, HttpStatus.FORBIDDEN));

    }

    private void handleAuthenticationException(AuthenticationException authenticationException, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        FlowCrmErrorResponse authenticationFailed;
        if (authenticationException instanceof BadCredentialsException badCredentialsException) {
            authenticationFailed = FlowCrmErrorResponse.of(badCredentialsException.getMessage(), FlowCrmErrorCode.AUTHENTICATION, HttpStatus.BAD_REQUEST);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else if (authenticationException instanceof DisabledException) {
            authenticationFailed = FlowCrmErrorResponse.of("User account is not active", FlowCrmErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED);
        } else if (authenticationException instanceof LockedException) {
            authenticationFailed = FlowCrmErrorResponse.of("User account is locked due to security policy", FlowCrmErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED);
        } else if (authenticationException instanceof JwtExpiredTokenException) {
            authenticationFailed = FlowCrmErrorResponse.of("Token has expired", FlowCrmErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);
        } else {
            authenticationFailed = FlowCrmErrorResponse.of("Authentication failed", FlowCrmErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED);
        }
        authenticationFailed.setDetails(getStringOfStackTrace(authenticationException));
        mapper.writeValue(response.getWriter(), authenticationFailed);
    }

    public void handleDatabaseException(SQLException ex, HttpServletResponse response) throws IOException {
        final HttpStatus[] responseStatus = {HttpStatus.INTERNAL_SERVER_ERROR};
        final FlowCrmErrorCode[] responseErrorCode = {FlowCrmErrorCode.UNKNOWN_ERROR_CODE};
        final String[] responseMessage = {ex.getMessage()};
        Optional<Map.Entry<Integer, DatabaseErrorTranslation>> message = databaseVendorErrorCodeProperties.getCodes().entrySet()
                .stream().filter(err -> err.getKey() == ex.getErrorCode()).findAny();
        message.ifPresent(msg -> {
            responseErrorCode[0] = msg.getValue().getErrorCode();
            responseMessage[0] = msg.getValue().getMessage();
            responseStatus[0] = HttpStatus.BAD_REQUEST;
        });
        response.setStatus(responseStatus[0].value());
        mapper.writeValue(response.getWriter(), FlowCrmErrorResponse.of(responseMessage[0], responseErrorCode[0], responseStatus[0]));
    }
}

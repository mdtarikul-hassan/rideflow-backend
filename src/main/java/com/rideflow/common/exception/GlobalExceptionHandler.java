package com.rideflow.common.exception;

import com.rideflow.common.dto.ApiErrorResponse;
import com.rideflow.common.web.ApiPaths;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RideflowException.class)
    public ResponseEntity<ApiErrorResponse> handleRideflowException(RideflowException ex, HttpServletRequest request) {
        HttpStatus status = ex.errorCode().httpStatus();
        if (status.is5xxServerError()) {
            log.error("RideflowException [{}]: {}", ex.errorCode(), ex.getMessage(), ex);
        } else {
            log.warn("RideflowException [{}]: {}", ex.errorCode(), ex.getMessage());
        }
        return build(status, ex.code(), ex.getMessage(), ex.details(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> fieldErrors.put(fe.getField(), fe.getDefaultMessage()));

        return build(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_FAILED.name(),
                "Request validation failed", fieldErrors, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        return build(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHENTICATED.name(), "Invalid credentials", null, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return build(HttpStatus.FORBIDDEN, ErrorCode.UNAUTHORIZED.name(), "Access denied", null, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception on {} {}", request.getMethod(), request.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_ERROR.name(),
                "An unexpected error occurred", null, request);
    }

    private ResponseEntity<ApiErrorResponse> build(HttpStatus status, String code, String message, Object details,
                                                   HttpServletRequest request) {
        ApiErrorResponse body = ApiErrorResponse.of(code, message, details, request.getRequestURI(),
                correlationId(request));
        return ResponseEntity.status(status).body(body);
    }

    private String correlationId(HttpServletRequest request) {
        Object id = request.getAttribute(ApiPaths.CORRELATION_ID_MDC_KEY);
        return id != null ? id.toString() : null;
    }
}

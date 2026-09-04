package com.rideflow.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    VALIDATION_FAILED(HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(HttpStatus.FORBIDDEN),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND),
    CONFLICT(HttpStatus.CONFLICT),
    RATE_LIMITED(HttpStatus.TOO_MANY_REQUESTS),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    DEPENDENCY_FAILURE(HttpStatus.BAD_GATEWAY);

    private final HttpStatus httpStatus;
    ErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    public HttpStatus httpStatus(){
        return httpStatus;
    }

}

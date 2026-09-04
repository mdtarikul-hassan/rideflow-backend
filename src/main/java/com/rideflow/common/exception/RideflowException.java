package com.rideflow.common.exception;

public class RideflowException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String code;
    private final Object details;

    public RideflowException(ErrorCode errorCode, String message) {
        this(errorCode, errorCode.name(), message, null);
    }

    public RideflowException(ErrorCode errorCode, String message, Object details) {
        this(errorCode, errorCode.name(), message, details);
    }

    public RideflowException(ErrorCode errorCode, String code, String message, Object details) {
        super(message);
        this.errorCode = errorCode;
        this.code = code;
        this.details = details;
    }
    public ErrorCode errorCode() {
        return errorCode;
    }

    public String code() {
        return code;
    }

    public Object details() {
        return details;
    }

    public static RideflowException notFound(String message){
        return new RideflowException(ErrorCode.RESOURCE_NOT_FOUND, message);
    }
    public static RideflowException conflict(String message) {
        return new RideflowException(ErrorCode.CONFLICT, message);
    }

    public static RideflowException badRequest(String message) {
        return new RideflowException(ErrorCode.INVALID_REQUEST, message);
    }

    public static RideflowException unauthorized(String message) {
        return new RideflowException(ErrorCode.UNAUTHORIZED, message);
    }

}

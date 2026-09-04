package com.rideflow.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        boolean success,
        ErrorDetail error,
        Instant timestamp,
        String path,
        String correlationalId

) {

    public record ErrorDetail(String code, String message, Object details) {
    }
    public static ApiErrorResponse of(String code, String message, Object details, String path, String correlationId) {
        return new ApiErrorResponse(false, new ErrorDetail(code, message, details), Instant.now(), path, correlationId);
    }
}

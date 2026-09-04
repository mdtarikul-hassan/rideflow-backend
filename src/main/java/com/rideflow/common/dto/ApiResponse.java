package com.rideflow.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(boolean success, T data, String message) {

    public static <T> ApiResponse <T> ok(T data){
        return new ApiResponse<T>(true, data, "Success");
    }

    public static <T> ApiResponse <T> ok(T data, String message){
        return new ApiResponse<T>(true, data, message);
    }

    public static ApiResponse <Void> okMessage(String message){
        return new ApiResponse<>(true, null, message);
    }

}

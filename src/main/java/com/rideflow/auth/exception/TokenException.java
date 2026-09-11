package com.rideflow.auth.exception;

import com.rideflow.common.exception.ErrorCode;
import com.rideflow.common.exception.RideflowException;

public class TokenException extends RideflowException {
    public TokenException(String code, String message) {
        super(ErrorCode.UNAUTHENTICATED, code, message, null);
    }

    public static TokenException missing(){
        return new TokenException("REFRESH_TOKEN_MISSING","No refresh token was provided");
    }

    public static TokenException invalid(){
        return new TokenException("REFRESH_TOKEN_INVALID","Invalid refresh token");
    }

    public static TokenException expired(){
        return new TokenException("REFRESH_TOKEN_EXPIRED","Token has expired");
    }

    public static TokenException reused(){
        return new TokenException("REFRESH_TOKEN_REUSE_DETECTED","This session is no longer valid. Please sign in again");
    }
}

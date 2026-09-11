package com.rideflow.auth.exception;

import com.rideflow.common.exception.ErrorCode;
import com.rideflow.common.exception.RideflowException;

import java.util.Map;

public class OtpException extends RideflowException {
    public OtpException(ErrorCode errorCode, String code, String message, Object details) {
        super(errorCode, code, message, details);
    }

    public static OtpException invalidCode(int attemptsRemaining) {
        return new OtpException(ErrorCode.INVALID_REQUEST, "OTP_INVALID", "The code you entered is incorrect", Map.of("attemptsRemaining", attemptsRemaining));
    }

    public static OtpException expired() {
        return new OtpException(ErrorCode.INVALID_REQUEST, "OTP_EXPIRED", "This code has expired. Request a new one", null);
    }

    public static OtpException maxAttemptsExceeded() {
        return new OtpException(ErrorCode.RATE_LIMITED, "OTP_MAX_ATTEMPT_EXCEEDED", "Too many incorrect attempts. Request a new code", null);
    }

    public static OtpException resendCooldown(long secondsRemaining) {
        return new OtpException(ErrorCode.RATE_LIMITED, "OTP_RESEND_COOLDOWN", "Please wait before requesting another code", Map.of("secondsRemaining", secondsRemaining));
    }

    public static OtpException rateLimited() {
        return new OtpException(ErrorCode.RATE_LIMITED, "OTP_RATE_LIMITED", "Too many OTP requests. Try again later", null);
    }
}

package com.rideflow.auth.otp.log;

public interface SmsSender {
    void sendOtp(String mobileNumber, String code);
}

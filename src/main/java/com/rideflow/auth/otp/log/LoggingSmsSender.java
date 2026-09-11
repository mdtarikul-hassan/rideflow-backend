package com.rideflow.auth.otp.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "default"})
@Slf4j
public class LoggingSmsSender implements SmsSender {

    @Override
    public void sendOtp(String mobileNumber, String code) {
        log.info("OTP dispatch requested for {} (no real SMS provider configured)", mask(mobileNumber));
    }

    private String mask(String mobileNumber){
        if(mobileNumber == null || mobileNumber.length() < 4){
            return "****";
        }
        return "*".repeat(mobileNumber.length() - 4) + mobileNumber.substring(mobileNumber.length() - 4);
    }
}

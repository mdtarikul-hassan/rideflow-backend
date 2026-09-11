package com.rideflow.auth.otp.entity;

public record OtpChallenge(String codeHash, int attemptsRemaining) {

    public OtpChallenge withAttemptsRemaining(int remaining) {
        return new OtpChallenge(codeHash, remaining);
    }
}

package com.rideflow.auth.otp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "otp_verifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "mobile_number", nullable = false, length = 10, unique = true)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", nullable = false, length = 20)
    private OtpPurpose purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",  nullable = false, length = 30)
    private OtpVerificationStatus status;

    @Column(name = "ip_address", length = 64)
    private String ipAddress;

    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    public static OtpVerification requested(String mobileNumber, OtpPurpose purpose, String ipAddress){
        OtpVerification v = new OtpVerification();
        v.mobileNumber = mobileNumber;
        v.purpose = purpose;
        v.status = OtpVerificationStatus.REQUESTED;
        v.ipAddress = ipAddress;
        v.requestedAt = Instant.now();

        return v;
    }

    public void markVerified(){
        this.status = OtpVerificationStatus.VERIFIED;
        this.verifiedAt = Instant.now();
    }

    public void markFailed(){
        this.status = OtpVerificationStatus.FAILED;
    }

}

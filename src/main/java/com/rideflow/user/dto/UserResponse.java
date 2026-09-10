package com.rideflow.user.dto;

import com.rideflow.user.entity.Gender;
import com.rideflow.user.entity.RoleName;
import com.rideflow.user.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String mobileNumber,
        String email,
        String firstName,
        String lastName,
        LocalDateTime dateOfBirth,
        Gender gender,
        boolean profileComplete,
        RoleName role
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getMobileNumber(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getGender(),
                user.isProfileComplete(),
                user.primaryRole()
        );
    }

}

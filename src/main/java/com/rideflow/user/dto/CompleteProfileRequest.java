package com.rideflow.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CompleteProfileRequest(
        @NotBlank(message = "First Name is required")
        String firstName,

        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email
) {
}

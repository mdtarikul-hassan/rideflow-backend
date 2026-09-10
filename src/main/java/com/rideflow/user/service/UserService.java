package com.rideflow.user.service;

import com.rideflow.user.entity.RoleName;
import com.rideflow.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User findOrCreatePassenger(String mobileNumber);
    User createBackOfficeUser(String email, String rawPassword, String mobileNumber, RoleName roleName, boolean mfaEnabled);
    boolean verifyPassword(User user, String rawPassword);
    Optional<User> findByEmail(String email);
    User getByEmail(String email);
    User getById(UUID id);
    User completeProfile(UUID userId, String firstName, String lastName, String email);
    void validatePasswordStrength(String rawPassword);
}

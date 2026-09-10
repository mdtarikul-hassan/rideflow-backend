package com.rideflow.user.service.impl;

import com.rideflow.common.exception.ErrorCode;
import com.rideflow.common.exception.RideflowException;
import com.rideflow.user.entity.Role;
import com.rideflow.user.entity.RoleName;
import com.rideflow.user.entity.User;
import com.rideflow.user.repo.RoleRepo;
import com.rideflow.user.repo.UserRepo;
import com.rideflow.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private static final Pattern PASSWORD_HAS_LETTERS = Pattern.compile("[A-Za-z]");
    private static final Pattern PASSWORD_HAS_DIGITS = Pattern.compile("[\\d]");

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User findOrCreatePassenger(String mobileNumber) {
        return userRepo.findByMobileNumber(mobileNumber)
                .orElseGet(() -> {
                    Role passengerRole = roleRepo.findByName(RoleName.PASSENGER)
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "PASSENGER role is missing - check the roles seed migration"
                            ));
                    return userRepo.save(User.newPassenger(mobileNumber, passengerRole));
                });
    }

    @Override
    @Transactional
    public User createBackOfficeUser(String email, String rawPassword, String mobileNumber, RoleName roleName, boolean mfaEnabled) {
        if(userRepo.existsByEmail(email)) {
            throw new RideflowException(ErrorCode.CONFLICT, "EMAIL_ALREADY_IN_USE",
                    "This Email associate with another account", null);
        }
        validatePasswordStrength(rawPassword);
        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role " + roleName+" is missing - check the roles seed migration"));

        User user = User.newBackOffice(email,passwordEncoder.encode(rawPassword),mobileNumber,role,mfaEnabled);

        return userRepo.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyPassword(User user, String rawPassword) {
        return user.getPassword() != null && passwordEncoder.matches(rawPassword, user.getPassword());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> RideflowException.notFound("No account found for this email"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(UUID id) {
        return userRepo.findById(id)
                .orElseThrow(() -> RideflowException.notFound("User not found with id: " + id));
    }

    @Override
    @Transactional
    public User completeProfile(UUID userId, String firstName, String lastName, String email) {
        User user = getById(userId);

        if(email != null && !email.equalsIgnoreCase(user.getEmail()) && userRepo.existsByEmail(email)) {
            throw new RideflowException(ErrorCode.CONFLICT, "EMAIL_ALREADY_IN_USE",
                    "This Email is already associate with another account",null);
        }

        user.completeProfile(firstName, lastName, email);

        return userRepo.save(user);
    }

    @Override
    public void validatePasswordStrength(String rawPassword) {
        if(rawPassword == null || rawPassword.length() < 8
        || !PASSWORD_HAS_LETTERS.matcher(rawPassword).find()
        || !PASSWORD_HAS_DIGITS.matcher(rawPassword).find()) {
            throw new RideflowException(ErrorCode.VALIDATION_FAILED, "WEAK PASSWORD",
                    "Password must be at least 8 character and include a letter and a digit", null);
        }
    }
}

package com.rideflow.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "mobile_number", unique = true, length = 13, nullable = false)
    private String mobileNumber;

    @Column(name = "email", unique = true)
    @Setter
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    @Setter
    private String firstName;

    @Column(name = "last_name")
    @Setter
    private String lastName;

    @Column(name = "date_of_birth")
    @Setter
    private LocalDateTime dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 15)
    private Gender gender;

    @Column(name = "profile_image_url")
    @Setter
    private String profileImageUrl;

    @Column(name = "emergency_contact_name")
    @Setter
    private String emergencyContactName;

    @Column(name = "emergency_contact_number")
    @Setter
    private String emergencyContactNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private UserStatus status;

    @Column(name = "profile_complete", nullable = false)
    private boolean profileComplete;

    @Column(name = "mfa_enabled",  nullable = false)
    private boolean mfaEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "Updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    public static User newPassenger(String mobileNumber, Role passengerRole){
        User user = new User();
        user.mobileNumber = mobileNumber;
        user.status = UserStatus.ACTIVE;
        user.profileComplete = false;
        user.roles.add(passengerRole);
        Instant now = Instant.now();
        user.createdAt = now;
        user.updatedAt = now;
        return user;
    }

    public static User newBackOffice(String email, String password, String mobileNumber,
                                     Role role, boolean mfaEnabled) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.mobileNumber = mobileNumber;
        user.status = UserStatus.ACTIVE;
        user.profileComplete = true;
        user.mfaEnabled = mfaEnabled;
        user.roles.add(role);
        Instant now = Instant.now();
        user.createdAt = now;
        user.updatedAt = now;
        return user;
    }

    public void completeProfile(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profileComplete = this.firstName != null && !this.firstName.isBlank()
                && this.email != null && !this.email.isBlank();
        this.updatedAt = Instant.now();
    }

    public boolean isActive(){
        return status == UserStatus.ACTIVE;
    }

    public RoleName primaryRole(){
        return roles.stream().findFirst().map(Role::getName).orElse(null);
    }

}

package com.rideflow.user.repo;

import com.rideflow.user.entity.Role;
import com.rideflow.user.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(RoleName name);
}

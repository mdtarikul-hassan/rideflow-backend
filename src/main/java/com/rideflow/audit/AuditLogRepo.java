package com.rideflow.audit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditLogRepo extends JpaRepository<AuditLog, UUID> {
}

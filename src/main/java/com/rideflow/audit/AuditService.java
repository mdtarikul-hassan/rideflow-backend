package com.rideflow.audit;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuditService {

    private final AuditLogRepo auditLogRepo;

    public AuditService(AuditLogRepo auditLogRepo) {
        this.auditLogRepo = auditLogRepo;
    }


    public void record(UUID actorId, String actorRole, String action, String targetType, String targetId,
                       String details, String correlationId) {
        auditLogRepo.save(new AuditLog(actorId, actorRole, action, targetType, targetId, details, correlationId));
    }
}

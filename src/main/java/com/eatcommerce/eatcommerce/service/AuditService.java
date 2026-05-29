package com.eatcommerce.eatcommerce.service;

import com.eatcommerce.eatcommerce.DTO.AuditLogDTO;
import com.eatcommerce.eatcommerce.entity.AuditLog;
import com.eatcommerce.eatcommerce.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void log(String userName, String action, String entity, String entityId, String details) {
        AuditLog log = new AuditLog();
        log.setUserName(userName);
        log.setAction(action);
        log.setEntity(entity);
        log.setEntityId(entityId);
        log.setDetails(details);
        auditLogRepository.save(log);
    }

    public List<AuditLogDTO> getLogs(String userName, String action, LocalDate startDate, LocalDate endDate) {
        List<AuditLog> all = auditLogRepository.findAllByOrderByTimestampDesc();

        return all.stream()
                .filter(log -> {
                    if (userName != null && !userName.isEmpty()) {
                        if (!log.getUserName().toLowerCase().contains(userName.toLowerCase())) return false;
                    }
                    if (action != null && !action.isEmpty()) {
                        if (!log.getAction().toLowerCase().contains(action.toLowerCase())) return false;
                    }
                    if (startDate != null) {
                        LocalDateTime start = startDate.atStartOfDay();
                        if (log.getTimestamp().isBefore(start)) return false;
                    }
                    if (endDate != null) {
                        LocalDateTime end = endDate.atTime(LocalTime.MAX);
                        if (log.getTimestamp().isAfter(end)) return false;
                    }
                    return true;
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AuditLogDTO convertToDTO(AuditLog log) {
        AuditLogDTO dto = new AuditLogDTO();
        dto.setId(log.getId());
        dto.setUserName(log.getUserName());
        dto.setAction(log.getAction());
        dto.setEntity(log.getEntity());
        dto.setEntityId(log.getEntityId());
        dto.setDetails(log.getDetails());
        dto.setTimestamp(log.getTimestamp());
        return dto;
    }
}
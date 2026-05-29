package com.eatcommerce.eatcommerce.controller;

import com.eatcommerce.eatcommerce.DTO.AuditLogDTO;
import com.eatcommerce.eatcommerce.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("/logs")
    public ResponseEntity<List<AuditLogDTO>> getLogs(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(auditService.getLogs(userName, action, startDate, endDate));
    }
}
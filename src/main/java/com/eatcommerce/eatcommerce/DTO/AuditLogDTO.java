package com.eatcommerce.eatcommerce.DTO;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDTO {

    private Long id;
    private String userName;
    private String action;
    private String entity;
    private String entityId;
    private String details;
    private LocalDateTime timestamp;
}
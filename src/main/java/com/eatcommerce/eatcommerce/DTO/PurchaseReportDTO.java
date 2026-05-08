package com.eatcommerce.eatcommerce.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReportDTO {

    private Long supplierId;
    private String supplierName;
    private Integer totalPurchases;
    private Double totalSpent;
    private String periodStart;
    private String periodEnd;
}
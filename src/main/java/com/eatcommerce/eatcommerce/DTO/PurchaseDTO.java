package com.eatcommerce.eatcommerce.DTO;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

    private Long id;
    private Long supplierId;
    private String supplierName;
    private String date;
    private String concept;
    private Double totalAmount;
    private String status;
    private List<PurchaseProductDTO> items;
}
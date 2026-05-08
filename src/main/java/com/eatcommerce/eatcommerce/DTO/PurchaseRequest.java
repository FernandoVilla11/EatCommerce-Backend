package com.eatcommerce.eatcommerce.DTO;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    private Long supplierId;
    private String purchaseDate;
    private String concept;
    private List<PurchaseProductDTO> items;
}
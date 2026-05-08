package com.eatcommerce.eatcommerce.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseProductDTO {

    private String itemName;
    private Integer quantity;
    private Double unitPrice;
}
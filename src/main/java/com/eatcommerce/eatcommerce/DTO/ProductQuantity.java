package com.eatcommerce.eatcommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantity {
    private Long productId;
    private Integer quantity;
}

package com.eatcommerce.eatcommerce.DTO.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessSoldProduct {
    private Long productId;
    private String productName;
    private Double salePrice;
    private Integer totalSold;
    private Double totalSalesValue;
}

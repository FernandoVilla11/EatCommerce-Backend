package com.eatcommerce.eatcommerce.DTO.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesCompare {
    private String productName;
    private Long totalUnitSold;
    private Double totalSalesValue;
}

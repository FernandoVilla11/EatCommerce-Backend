package com.eatcommerce.eatcommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEdit {
    String productName;
    Double netPrice;
    Double profitMargin;
}

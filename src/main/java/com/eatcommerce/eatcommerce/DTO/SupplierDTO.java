package com.eatcommerce.eatcommerce.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
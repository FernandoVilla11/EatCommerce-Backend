package com.eatcommerce.eatcommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long supplierId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String nit;

    @Column
    private String contactName;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private String contractDescription;
}
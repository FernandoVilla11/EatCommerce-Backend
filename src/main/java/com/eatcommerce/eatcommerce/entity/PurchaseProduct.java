package com.eatcommerce.eatcommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long purchaseProductId;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double unitPrice;
}
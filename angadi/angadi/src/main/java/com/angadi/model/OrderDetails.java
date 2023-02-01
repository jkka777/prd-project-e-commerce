package com.angadi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailsId;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Orders orders;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;

    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "supplierId")
    private Suppliers suppliers;
}

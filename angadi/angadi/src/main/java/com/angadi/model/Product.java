package com.angadi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private String productImage;
    private Long productPrice;
    private Double productRatings;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}

package com.angadi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @NotBlank(message = "Product name cannot be blank")
    @NotEmpty(message = "Product name cannot be empty")
    @NotNull(message = "Product name cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}", message = "Product name should contain minimum of 4 and maximum of 15 characters " +
            "and can contain a-z or A-Z or 0-9")
    private String productName;

    private String productImage;

    @NotBlank(message = "Price cannot be blank")
    @NotEmpty(message = "Price cannot be empty")
    @NotNull(message = "Price cannot be null")
    @Min(0)
    private Long productPrice;

    @NotBlank(message = "Price cannot be blank")
    @NotEmpty(message = "Price cannot be empty")
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double productRatings;

    @NotBlank(message = "Stock cannot be blank")
    @NotEmpty(message = "Stock cannot be empty")
    @NotNull(message = "Stock cannot be null")
    @DecimalMin(value = "0.0")
    private Double productStock;

    @NotBlank(message = "Quantity cannot be blank")
    @NotEmpty(message = "Quantity cannot be empty")
    @NotNull(message = "Quantity cannot be null")
    @DecimalMin(value = "0.0")
    private Double productQuantity;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToOne
    @PrimaryKeyJoinColumn
    private OrderDetails orderDetails;

}

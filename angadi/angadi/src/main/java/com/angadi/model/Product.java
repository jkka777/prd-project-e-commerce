package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    @Digits(integer = 7, fraction = 2, message = "Product price cannot be more than 7 digit")
    private Integer productPrice;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double productRatings;

    @Digits(integer = 6, fraction = 2, message = "Product stock cannot be more than 6 digit")
    private Integer productStock;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId")
    private Category category;

    @JsonIgnore
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private OrderDetails orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<CartItem> cartItems = new HashSet<>();
}

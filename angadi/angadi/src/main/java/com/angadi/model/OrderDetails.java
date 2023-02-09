package com.angadi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailsId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private Orders orders;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "")
    private Product product;

    @NotBlank(message = "Quantity cannot be blank")
    @NotEmpty(message = "Quantity cannot be empty")
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Minimum quantity cannot be less than 0")
    @Max(value = 10, message = "Maximum quantity cannot be greater than 10")
    private Integer quantity;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Suppliers suppliers;
}

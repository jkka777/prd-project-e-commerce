package com.angadi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @NotBlank(message = "Date cannot be blank")
    @NotEmpty(message = "Date cannot be empty")
    @NotNull(message = "Date cannot be null")
    private LocalDate orderDate;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<Product> orderedItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    /* one to one uni-directional */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private Payments payments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shippingId")
    private Shipping shipping;

    @NotBlank(message = "Date cannot be blank")
    @NotEmpty(message = "Date cannot be empty")
    @NotNull(message = "Date cannot be null")
    private LocalDate deliveryDate;

    @NotBlank(message = "Total Price cannot be blank")
    @NotEmpty(message = "Total Price cannot be empty")
    @NotNull(message = "Total Price cannot be null")
    private Double totalOrderPrice;
}

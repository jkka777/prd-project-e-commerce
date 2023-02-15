package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @JsonIgnore
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<OrderDetails> orderDetails = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private Customer customer;

    /* one to one uni-directional */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private WalletTransactions walletTransactions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address deliveryAddress;

    @JsonIgnore
    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private Shipping shipping;

    private Boolean deliveryStatus = false;

    private LocalDate deliveryDate;

    @JsonIgnore
    @NotBlank(message = "Total Price cannot be blank")
    @NotEmpty(message = "Total Price cannot be empty")
    @NotNull(message = "Total Price cannot be null")
    private Double totalOrderPrice;
}

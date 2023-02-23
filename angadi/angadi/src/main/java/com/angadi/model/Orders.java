package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer orderId;

    private LocalDate orderDate;

    private String orderStatus = "NOT_SHIPPED";

    private Integer totalOrderPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address deliveryAddress;

    /* one to one uni-directional */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentsId")
    private Payments payments;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private Shipping shipping;

    private LocalDate deliveryDate;

    @JsonIgnore
    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private WalletTransactions walletTransactions;
}

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private LocalDate orderDate;

    @JsonIgnore
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

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

    private Integer totalOrderPrice;
}

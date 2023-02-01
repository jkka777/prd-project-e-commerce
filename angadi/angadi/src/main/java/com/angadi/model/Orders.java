package com.angadi.model;

import jakarta.persistence.*;
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

    private LocalDate deliveryDate;

    private Double totalOrderPrice;
}

package com.angadi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    private String street;

    private String city;

    private String pinCode;

    private String state;

    private String country;

    private AddressType addressType;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}

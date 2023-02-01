package com.angadi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Suppliers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierId;

    private String supplierName;

    private String phone;

    private String email;

    private String street;

    private String city;

    private String pinCode;

    private String state;

    private String country;

}

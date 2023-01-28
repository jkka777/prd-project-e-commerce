package com.angadi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private String customerPassword;
    private String customerCity;
    private String customerPinCode;
    private String customerState;
    private RoleType role;

}

package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    @NotBlank(message = "Street cannot be blank")
    @NotEmpty(message = "Street cannot be empty")
    @NotNull(message = "Street cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}", message = "City name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String street;

    @NotBlank(message = "City cannot be blank")
    @NotEmpty(message = "City cannot be empty")
    @NotNull(message = "City cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}", message = "City name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String city;

    @NotBlank(message = "Pin-code cannot be blank")
    @NotEmpty(message = "Pin-code cannot be empty")
    @NotNull(message = "Pin-code cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}", message = "Pin-code  can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String pinCode;

    @NotBlank(message = "State cannot be blank")
    @NotEmpty(message = "State cannot be empty")
    @NotNull(message = "State cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}", message = "State name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String state;

    @NotBlank(message = "Country cannot be blank")
    @NotEmpty(message = "Country cannot be empty")
    @NotNull(message = "Country cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}", message = "Country name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String country;

    @NotNull
    @NotBlank
    @NotEmpty
    private AddressType addressType;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @JsonIgnore
    private Integer orderId;
}

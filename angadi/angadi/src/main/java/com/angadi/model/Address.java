package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer addressId;
    @NotBlank(message = "Street cannot be blank")
    @NotEmpty(message = "Street cannot be empty")
    @NotNull(message = "Street cannot be null")
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z0-9 .]*${4,55}", message = "Street name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String street;

    @NotBlank(message = "City cannot be blank")
    @NotEmpty(message = "City cannot be empty")
    @NotNull(message = "City cannot be null")
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z0-9 .]*${4,55}", message = "City name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String city;

    @NotBlank(message = "Pin-code cannot be blank")
    @NotEmpty(message = "Pin-code cannot be empty")
    @NotNull(message = "Pin-code cannot be null")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Pin-code should contain 1-9 digit and " +
            "should have length of 6 and if pin code is not of 6 digit then add 0 to front to become 6 in size.")
    private String pinCode;

    @NotBlank(message = "State cannot be blank")
    @NotEmpty(message = "State cannot be empty")
    @NotNull(message = "State cannot be null")
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*${4,55}", message = "State name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String state;

    @NotBlank(message = "Country cannot be blank")
    @NotEmpty(message = "Country cannot be empty")
    @NotNull(message = "Country cannot be null")
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*${4,55}", message = "Country name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String country;

    private AddressType addressType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    /* uni-directional */
    @JsonIgnore
    private Integer orderId;
}

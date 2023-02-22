package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @NotBlank(message = "Name cannot be blank")
    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be null")
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*${4,55}", message = "Name should contain minimum of 4 and maximum of 55 characters and can contain a-z or A-Z or 0-9")
    private String name;

    @NotBlank(message = "Mobile cannot be blank")
    @NotEmpty(message = "Mobile cannot be empty")
    @NotNull(message = "Mobile cannot be null")
    @Pattern(regexp = "^[6-9][0-9]{9}", message = "Enter valid 10 digit mobile number")
    private String mobile;

    @NotBlank(message = "Email cannot be blank")
    @NotEmpty(message = "Email cannot be empty")
    @NotNull(message = "Email cannot be null")
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @NotEmpty(message = "Password cannot be empty")
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}", message = "Password length " +
            "should be greater than 8 and should contain one small case, one upper case, one digit and one special" +
            " character")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

    private CustomerType customerType;

    private LocalDateTime registeredTime;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Orders> orders = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Wallet wallet;

    @JsonIgnore
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    @JsonIgnore
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Product product;
}

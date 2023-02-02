package com.angadi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @NotBlank(message = "Name cannot be blank")
    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,25}", message = "Name should contain minimum of 4 and maximum of 25 characters and can contain a-z or A-Z or 0-9")
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

    @NotBlank(message = "Email cannot be blank")
    @NotEmpty(message = "Email cannot be empty")
    @NotNull(message = "Email cannot be null")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$", message = "Password length " +
            "should be greater than 8 and should contain one small case, one upper case, one number and one special" +
            "character")
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

    @NotNull
    @NotBlank
    @NotEmpty
    private CustomerType customerType;

    @NotNull
    @NotBlank
    @NotEmpty
    private LocalDateTime registeredTime;
}

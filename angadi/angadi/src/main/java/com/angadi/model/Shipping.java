package com.angadi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shippingId;

    private ShippingType shippingType;

    @NotBlank(message = "Company name cannot be blank")
    @NotEmpty(message = "Company name cannot be empty")
    @NotNull(message = "Company name cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}", message = "Company name can contain a-z or A-Z or 0-9 characters and " +
            "should have length of minimum 4 and maximum of 20 characters")
    private String shippingCompany;

    @NotBlank(message = "Mobile cannot be blank")
    @NotEmpty(message = "Mobile cannot be empty")
    @NotNull(message = "Mobile cannot be null")
    @Pattern(regexp = "^[6-9][0-9]{9}", message = "Enter valid 10 digit mobile number")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Orders orders;

}

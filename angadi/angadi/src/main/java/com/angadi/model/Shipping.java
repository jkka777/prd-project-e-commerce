package com.angadi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shippingId;

    @NotBlank(message = "Shipping Type cannot be blank")
    @NotEmpty(message = "Shipping Type cannot be empty")
    @NotNull(message = "Shipping Type cannot be null")
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

    @NotBlank(message = "Mobile cannot be blank")
    @NotEmpty(message = "Mobile cannot be empty")
    @NotNull(message = "Mobile cannot be null")
    @Min(1)
    private Integer orderId;

}

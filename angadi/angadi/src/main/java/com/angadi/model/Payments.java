package com.angadi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentsId;

    @NotBlank(message = "Payment type cannot be blank")
    @NotEmpty(message = "Payment type Id cannot be empty")
    @NotNull(message = "Payment type Id cannot be null")
    private PaymentType paymentType;

    @NotBlank(message = "Order Id cannot be blank")
    @NotEmpty(message = "Order Id cannot be empty")
    @NotNull(message = "Order Id cannot be null")
    @Min(1)
    private Integer orderId;

}

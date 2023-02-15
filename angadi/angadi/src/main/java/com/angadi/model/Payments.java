package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentsId;

    private PaymentType paymentType;

    private LocalDate paymentDate;

    @JsonIgnore
    @NotBlank(message = "Order cannot be blank")
    @NotEmpty(message = "Order cannot be empty")
    @NotNull(message = "Order cannot be null")
    @OneToOne
    @PrimaryKeyJoinColumn
    private Orders orders;

}

package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class WalletTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walletTransactionId;

    @NotNull
    @NotBlank
    @NotEmpty
    private LocalDateTime transactionTime;

    @NotNull(message = "Amount cannot be null")
    @NotBlank(message = "Amount cannot be blank")
    @NotEmpty(message = "Amount cannot be empty")
    @DecimalMin(value = "0.0", message = "Please enter valid amount, It should be greater than 0!")
    private Double amount;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "walletId")
    private Wallet wallet;

    private Integer orderId;

}

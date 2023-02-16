package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walletTransactionId;

    private LocalDateTime transactionTime;

    /*@NotNull(message = "Amount cannot be null")
    @NotBlank(message = "Amount cannot be blank")
    @NotEmpty(message = "Amount cannot be empty")
    @DecimalMin(value = "0.0", message = "Please enter valid amount, It should be greater than 0!")*/
    @Digits(integer = 5, fraction = 2, message = "Amount cannot be more than 10000 and less than 100")
    private Integer amount;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "walletId")
    private Wallet wallet;

    private Integer orderId;

}

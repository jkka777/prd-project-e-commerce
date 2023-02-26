package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mysql_sequence")
    @GenericGenerator(name = "mysql_sequence", strategy = "com.angadi.generator.MySQLSequenceGenerator")
    private Integer walletTransactionId;

    private LocalDateTime transactionTime;

    @Digits(integer = 6, fraction = 2, message = "Amount cannot be more than 100000 and less than 100")
    private Integer amount;

    /*@NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @NotEmpty(message = "Description cannot be empty")*/
    private String description;

    @ManyToOne
    @JoinColumn(name = "walletId")
    private Wallet wallet;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Orders orders;

    private TransactionStatus transactionStatus = TransactionStatus.DUE;

}

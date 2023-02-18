package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walletId;

    @Digits(integer = 5, fraction = 2, message = "Balance you want to add should not be less than 100 and more than 10000")
    private Integer walletBalance;

    @JsonIgnore
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<WalletTransactions> walletTransactions = new HashSet<>();

    @OneToOne(mappedBy = "wallet", cascade = CascadeType.ALL)
    private Customer customer;

}

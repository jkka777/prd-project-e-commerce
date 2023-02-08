package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.WalletException;
import com.angadi.model.Wallet;

public interface WalletService {

    public Wallet addWallet(Wallet wallet, String email) throws CustomerException;

    public Wallet addBalanceToWallet(Double amount, String email) throws CustomerException, WalletException;

    public Double showBalance(Integer walletId, String email) throws WalletException, CustomerException;

    public Wallet transferAmount(Integer walletId, String description, Double amount, String email) throws WalletException, CustomerException;
}

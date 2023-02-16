package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.WalletException;
import com.angadi.model.Wallet;

public interface WalletService {

    public Wallet addWallet(Wallet wallet, String email) throws CustomerException;

    public Wallet addBalanceToWallet(Integer amount, String email) throws CustomerException, WalletException;

    public Integer showBalance(Integer walletId, String email) throws WalletException, CustomerException;

    public Wallet transferAmount(Integer walletId, String description, Integer amount, String email) throws WalletException, CustomerException;
}

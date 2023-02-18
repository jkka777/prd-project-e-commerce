package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.WalletException;
import com.angadi.model.Wallet;

public interface WalletService {

    public Wallet addWallet(Wallet wallet) throws CustomerException;

    public Wallet addBalanceToWallet(Integer amount) throws CustomerException, WalletException;

    public Integer showBalance() throws WalletException, CustomerException;

    public Wallet transferAmount(Integer walletId, String description, Integer amount) throws WalletException, CustomerException;
}

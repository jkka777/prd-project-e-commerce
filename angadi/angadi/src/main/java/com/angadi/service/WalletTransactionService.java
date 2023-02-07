package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.WalletException;
import com.angadi.exception.WalletTransactionException;
import com.angadi.model.WalletTransactions;

import java.util.Set;

public interface WalletTransactionService {

    public WalletTransactions addTransaction(WalletTransactions walletTransactions, String email) throws CustomerException, WalletException;

    public Set<WalletTransactions> viewAllTransactions(String email) throws CustomerException, WalletTransactionException;


}

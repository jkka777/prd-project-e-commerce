package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.exception.WalletException;
import com.angadi.exception.WalletTransactionException;
import com.angadi.model.WalletTransactions;

import java.util.Set;

public interface WalletTransactionService {

    public WalletTransactions addTransaction(WalletTransactions walletTransactions) throws CustomerException, WalletException;

    public WalletTransactions makePaymentToOrder(WalletTransactions walletTransactions, Integer orderId) throws CustomerException, WalletException, OrderException;

    public Set<WalletTransactions> viewTransaction() throws CustomerException, WalletException, WalletTransactionException;

    public Set<WalletTransactions> viewAllTransactions(String email) throws CustomerException, WalletTransactionException;


}

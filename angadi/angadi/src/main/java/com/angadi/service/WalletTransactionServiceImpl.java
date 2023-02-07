package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.WalletException;
import com.angadi.exception.WalletTransactionException;
import com.angadi.model.Customer;
import com.angadi.model.Wallet;
import com.angadi.model.WalletTransactions;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.WalletRepository;
import com.angadi.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Override
    public WalletTransactions addTransaction(WalletTransactions walletTransactions, String email) throws CustomerException, WalletException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Wallet wallet = customer.getWallet();

            if (wallet != null) {

                Set<WalletTransactions> transactions = wallet.getWalletTransactions();

                walletTransactions.setWallet(wallet);
                walletTransactions.setTransactionTime(LocalDateTime.now());
                walletTransactions.setAmount(walletTransactions.getAmount());
                walletTransactions.setDescription(walletTransactions.getDescription());

                transactions.add(walletTransactions);

                walletRepository.save(wallet);
                walletTransactionRepository.save(walletTransactions);
            }
            throw new WalletException("No Wallet found with given email! Please add wallet to your profile  -> " + email);
        }
        throw new CustomerException("No Customer found with given email -> " + email);
    }

    @Override
    public Set<WalletTransactions> viewAllTransactions(String email) throws CustomerException, WalletTransactionException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            List<WalletTransactions> transactions = walletTransactionRepository.findAll();

            if (!transactions.isEmpty()) return new HashSet<>(transactions);
            else
                throw new WalletTransactionException("No transactions found for your profile, " +
                        "Please add balance to wallet and view some time after!");
        }
        throw new CustomerException("No Customer found with given email -> " + email);
    }
}

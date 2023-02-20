package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.WalletException;
import com.angadi.model.Customer;
import com.angadi.model.Wallet;
import com.angadi.model.WalletTransactions;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.WalletRepository;
import com.angadi.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public Wallet addWallet(Wallet wallet) throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            customer.setWallet(wallet);
            wallet.setCustomer(customer);

            return walletRepository.save(wallet);
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Wallet addBalanceToWallet(Integer amount) throws CustomerException, WalletException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Wallet> optional = walletRepository.findById(customer.getWallet().getWalletId());

            if (optional.isPresent()) {

                Wallet w = optional.get();
                w.setWalletBalance(w.getWalletBalance() + amount);

                customer.setWallet(w);

                return walletRepository.save(w);
            }
            throw new WalletException("No wallet found! Add wallet to your profile!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Integer showBalance() throws WalletException, CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Optional<Wallet> optional = walletRepository.findById(customer.getWallet().getWalletId());

            if (optional.isPresent()) {
                return optional.get().getWalletBalance();
            }
            throw new WalletException("No wallet found! Add wallet to your profile!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    @Override
    public Wallet transferAmount(Integer destWalletId, String description, Integer transferAmount) throws WalletException, CustomerException {

        /*
        1. First get the Destination customer by email/wallet Id in our db
        2. Get his wallet
        3. Check whether the source customer wallet has enough amount to transfer
        4. Record the wallet transaction for source customer and set details for the amount for transferring
        5. Set the wallet transaction for source customer wallet
        6. Repeat the above steps for Destination customer
        */

        Customer sourceCustomer = currentUser.getLoggedInCustomer();

        if (sourceCustomer != null) {

            Optional<Wallet> optional = walletRepository.findById(sourceCustomer.getWallet().getWalletId());

            if (optional.isPresent()) {

                Wallet sourceWallet = optional.get();

                Integer sourceBalance = sourceWallet.getWalletBalance();

                if (transferAmount > sourceBalance) {
                    throw new WalletException("Insufficient amount in wallet! Please add balance to proceed fund transfer.");
                }
                sourceWallet.setWalletBalance(sourceBalance - transferAmount);

                /* For recording new wallet transaction, create object */

                WalletTransactions walletTransactions = new WalletTransactions();

                walletTransactions.setTransactionTime(LocalDateTime.now());
                walletTransactions.setAmount(transferAmount);
                walletTransactions.setWallet(sourceWallet);
                walletTransactions.setDescription(description);

                Set<WalletTransactions> transactions = sourceWallet.getWalletTransactions();
                transactions.add(walletTransactions);

                sourceWallet.setWalletTransactions(transactions);

                sourceCustomer.setWallet(sourceWallet);
                sourceWallet.setCustomer(sourceCustomer);

                walletRepository.save(sourceWallet);

                Optional<Wallet> desOpt = walletRepository.findById(destWalletId);

                if (desOpt.isPresent()) {

                    Wallet destinationWallet = desOpt.get();

                    Customer destinationCustomer = destinationWallet.getCustomer();

                    if (destinationCustomer != null) {

                        WalletTransactions wt = new WalletTransactions();

                        wt.setTransactionTime(LocalDateTime.now());
                        wt.setAmount(transferAmount);
                        wt.setWallet(destinationWallet);
                        wt.setDescription(description);

                        Set<WalletTransactions> destWalletTransactionSet = destinationWallet.getWalletTransactions();
                        destWalletTransactionSet.add(wt);

                        destinationWallet.setWalletTransactions(destWalletTransactionSet);

                        Integer destBalance = destinationWallet.getWalletBalance();

                        destinationWallet.setWalletBalance(destBalance + transferAmount);

                        destinationCustomer.setWallet(destinationWallet);
                        destinationWallet.setCustomer(destinationCustomer);

                        walletRepository.save(destinationWallet);

                        return sourceWallet;
                    }
                    throw new CustomerException("No Customer found with the given wallet id -> " + destWalletId);
                }
                throw new WalletException("No destination wallet found. Please enter proper wallet id you want to transfer");
            }
            throw new WalletException("No wallet found! Please add a wallet to your profile..");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }
}

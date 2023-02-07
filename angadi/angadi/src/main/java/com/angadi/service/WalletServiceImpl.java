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

    @Override
    public Wallet addWallet(Wallet wallet, String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            wallet.setWalletBalance(wallet.getWalletBalance());
            wallet.setCustomer(customer);

            customer.setWallet(wallet);
            customerRepository.save(customer);

            return walletRepository.save(wallet);
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public Wallet addBalanceToWallet(Double amount, String email) throws CustomerException, WalletException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Wallet> optional = walletRepository.findById(customer.getWallet().getWalletId());

            if (optional.isPresent()) {

                Wallet w = optional.get();
                w.setWalletBalance(w.getWalletBalance() + amount);

                customer.setWallet(w);
                customerRepository.save(customer);

                return walletRepository.save(w);
            }
            throw new WalletException("No wallet found with given email > " + email);
        }
        throw new CustomerException("No Customer found with given email > " + email);
    }

    @Override
    public Double showBalance(Integer walletId, String email) throws WalletException, CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {

            Optional<Wallet> optional = walletRepository.findById(walletId);

            if (optional.isPresent()) {
                return optional.get().getWalletBalance();
            }
            throw new WalletException("No wallet found with given wallet id > " + walletId);
        }
        throw new CustomerException("No customer found with given email -> " + email);
    }

    @Override
    public Customer transferAmount(Integer walletId, String description, Double transferAmount, String email) throws WalletException, CustomerException {

        Customer sourceCustomer = customerRepository.findByEmail(email);

        if (sourceCustomer != null) {

            Optional<Wallet> optional = walletRepository.findById(sourceCustomer.getWallet().getWalletId());

            if (optional.isPresent()) {

                Wallet sourceWallet = optional.get();

                Double sourceBalance = sourceWallet.getWalletBalance();

                if (transferAmount > sourceBalance) {
                    throw new WalletException("Insufficient amount in wallet! Please add balance to proceed fund transfer.");
                }
                sourceWallet.setWalletBalance(sourceBalance - transferAmount);

                /* For recording new wallet transaction, create object */

                WalletTransactions walletTransactions = new WalletTransactions();
                WalletTransactionService swts = new WalletTransactionServiceImpl();

                walletTransactions.setTransactionTime(LocalDateTime.now());
                walletTransactions.setAmount(transferAmount);
                walletTransactions.setWallet(sourceWallet);
                walletTransactions.setDescription(description);

                /* Saving the new wallet transaction from above object by calling add function of Wallet transaction services */

                WalletTransactions sourceWalletTransaction = swts.addTransaction(walletTransactions, email);

                /* set the above saved transaction to customer wallet */

                Set<WalletTransactions> transactions = sourceWallet.getWalletTransactions();
                transactions.add(sourceWalletTransaction);

                sourceWallet.setWalletTransactions(transactions);

                sourceCustomer.setWallet(sourceWallet);
                sourceWallet.setCustomer(sourceCustomer);

                customerRepository.save(sourceCustomer);
                walletRepository.save(sourceWallet);

                Optional<Wallet> desOpt = walletRepository.findById(walletId);

                if (desOpt.isPresent()) {

                    Wallet destinationWallet = desOpt.get();

                    Customer destinationCustomer = customerRepository.findByEmail(destinationWallet.getCustomer().getEmail());

                    if (destinationCustomer != null) {

                        /* For recording new wallet transaction, create object */

                        WalletTransactions wt = new WalletTransactions();
                        WalletTransactionService dwts = new WalletTransactionServiceImpl();

                        wt.setTransactionTime(LocalDateTime.now());
                        wt.setAmount(transferAmount);
                        wt.setWallet(destinationWallet);
                        wt.setDescription(description);

                        /* Saving the new wallet transaction from above object by calling add function of Wallet transaction services */

                        WalletTransactions destinationWalletTransaction = dwts.addTransaction(wt, destinationCustomer.getEmail());

                        /* set the above saved transaction to customer wallet */

                        Set<WalletTransactions> destWalletTransactionSet = destinationWallet.getWalletTransactions();
                        destWalletTransactionSet.add(destinationWalletTransaction);

                        destinationWallet.setWalletTransactions(destWalletTransactionSet);

                        Double destBalance = destinationWallet.getWalletBalance();

                        destinationWallet.setWalletBalance(destBalance + transferAmount);

                        destinationCustomer.setWallet(destinationWallet);
                        destinationWallet.setCustomer(destinationCustomer);

                        customerRepository.save(destinationCustomer);
                        walletRepository.save(destinationWallet);
                    }
                    throw new CustomerException("No Customer found with the given wallet id -> " + walletId);
                }
                throw new WalletException("No wallet found with given wallet id -> " + walletId);
            }
            throw new WalletException("Invalid wallet details provided! Please enter valid email to get wallet -> " + email);
        }
        throw new CustomerException("Email that you provided is not registered! " + email);
    }
}

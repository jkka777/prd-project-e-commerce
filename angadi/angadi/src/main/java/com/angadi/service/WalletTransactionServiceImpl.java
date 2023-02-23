package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.exception.WalletException;
import com.angadi.exception.WalletTransactionException;
import com.angadi.model.*;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.OrderRepository;
import com.angadi.repository.WalletRepository;
import com.angadi.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public WalletTransactions addTransaction(WalletTransactions walletTransactions) throws CustomerException, WalletException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Wallet wallet = customer.getWallet();

            if (wallet != null) {

                Set<WalletTransactions> transactions = wallet.getWalletTransactions();

                walletTransactions.setWallet(wallet);
                walletTransactions.setTransactionTime(LocalDateTime.now());
                walletTransactions.setAmount(walletTransactions.getAmount());
                walletTransactions.setDescription(walletTransactions.getDescription());
                walletTransactions.setTransactionStatus(TransactionStatus.PAID);

                transactions.add(walletTransactions);
                wallet.setWalletTransactions(transactions);

                walletTransactionRepository.save(walletTransactions);
            }
            throw new WalletException("No wallet found! Add wallet to your profile!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }

    /* make payment to orders from the customer wallet */
    @Override
    public WalletTransactions makePaymentToOrder(WalletTransactions walletTransactions, Integer orderId) throws CustomerException, WalletException, OrderException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Wallet srcWallet = customer.getWallet();

            if (srcWallet != null) {

                Set<Orders> cOrders = customer.getOrders();

                if (cOrders.isEmpty()) {
                    throw new OrderException("No order found with given order Id -> " + orderId);
                }
                for (Orders o : cOrders) {
                    if (o.getOrderId() == orderId) {

                        Integer amount = 0;

                        amount = o.getTotalOrderPrice();

                        if (amount > srcWallet.getWalletBalance()) {
                            throw new WalletException("Insufficient funds! please add balance to you wallet!");
                        }
                        walletTransactions.setTransactionTime(LocalDateTime.now());
                        walletTransactions.setAmount(amount);
                        walletTransactions.setDescription("Product purchase");
                        walletTransactions.setWallet(srcWallet);
                        walletTransactions.setTransactionStatus(TransactionStatus.PAID);

                        o.setWalletTransactions(walletTransactions);
                        walletTransactions.setOrders(o);

                        Set<WalletTransactions> srcWtSet = srcWallet.getWalletTransactions();
                        srcWtSet.add(walletTransactions);
                        srcWallet.setWalletTransactions(srcWtSet);

                        srcWallet.setWalletBalance(srcWallet.getWalletBalance() - amount);

                        srcWallet.setCustomer(customer);
                        customer.setWallet(srcWallet);

                        return walletTransactionRepository.save(walletTransactions);
                    }
                }
            }
            throw new WalletException("No wallet found! Add wallet to your profile!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }


    /* user specific method for getting list of transactions */
    @Override
    public Set<WalletTransactions> viewTransaction() throws CustomerException, WalletException, WalletTransactionException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {

            Wallet wallet = customer.getWallet();

            if (wallet != null) {
                return wallet.getWalletTransactions();
            }
            throw new WalletException("No wallet found! Add wallet to your profile!");
        }
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }


    /* admin specific method where you get all transaction details */
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
        throw new CustomerException("Invalid user name/password provided or Please login first!");
    }
}

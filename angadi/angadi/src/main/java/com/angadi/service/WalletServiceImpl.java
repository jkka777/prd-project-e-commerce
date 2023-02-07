package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.exception.WalletException;
import com.angadi.model.Customer;
import com.angadi.model.Wallet;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletRepository walletRepository;
    private Optional<Wallet> desOpt;

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
    public Customer transferAmount(Integer walletId, Double amount, String email) throws WalletException, CustomerException {

        Customer sourceCustomer = customerRepository.findByEmail(email);

        if (sourceCustomer != null) {

            Optional<Wallet> optional = walletRepository.findById(sourceCustomer.getWallet().getWalletId());

            if (optional.isPresent()) {

                Wallet sourceWallet = optional.get();
                sourceWallet.setWalletBalance(sourceWallet.getWalletBalance() - amount);

                sourceCustomer.setWallet(sourceWallet);
                sourceWallet.setCustomer(sourceCustomer);

                customerRepository.save(sourceCustomer);
                walletRepository.save(sourceWallet);

                Optional<Wallet> desOpt = walletRepository.findById(walletId);

                if (desOpt.isPresent()) {

                    Wallet destinationWallet = desOpt.get();

                    Customer destinationCustomer = customerRepository.findByEmail(destinationWallet.getCustomer().getEmail());

                    if (destinationCustomer != null) {

                        destinationWallet.setWalletBalance(destinationWallet.getWalletBalance() + amount);

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

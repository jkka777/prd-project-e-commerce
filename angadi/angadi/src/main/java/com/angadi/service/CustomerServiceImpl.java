package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.model.Address;
import com.angadi.model.Customer;
import com.angadi.model.Wallet;
import com.angadi.repository.AddressRepository;
import com.angadi.repository.CustomerRepository;
import com.angadi.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public Customer saveCustomer(Customer customer) {

        customer.setRegisteredTime(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) throws CustomerException {

        Customer rc = customerRepository.findByEmail(customer.getEmail());

        if (rc == null) {
            throw new CustomerException("No customer found with given Email or Invalid user name entered, Please login...");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer deleteCustomer(String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {
            customerRepository.delete(customer);
            return customer;
        }
        throw new CustomerException("No customer found with given Email or Invalid user name entered, Please login...");
    }

    @Override
    public Customer getCustomerDetails() throws CustomerException {

        Customer customer = currentUser.getLoggedInCustomer();

        if (customer != null) {
            return customer;
        }
        throw new CustomerException("No customer found with given Email or Invalid user name entered, Please login...");
    }

    /*admin specific method*/
    @Override
    public List<Customer> getAllCustomerDetails() throws CustomerException {
        return customerRepository.findAll();
    }
}

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

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Customer saveCustomer(Customer customer) {

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) throws CustomerException {

        Customer rc = customerRepository.findByEmail(customer.getEmail());

        if (rc == null) {
            throw new CustomerException("No customer found with given Email -> " + customer.getEmail());
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
        throw new CustomerException("No customer found with given Email -> " + email);
    }

    @Override
    public Customer getCustomerDetails(String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {
            return customer;
        }
        throw new CustomerException("No customer found with given Email -> " + email);
    }

    @Override
    public List<Customer> getAllCustmerDetails() throws CustomerException {
        return customerRepository.findAll();
    }

    @Override
    public Set<Address> addAddress(Address address, String email) throws CustomerException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer == null) {
            throw new CustomerException("No customer found with given Email -> " + email);
        } else {
            Set<Address> addressList = customer.getAddresses();

            addressList.add(address);
            customer.setAddresses(addressList);

            address.setCustomer(customer);

            customerRepository.save(customer);
            addressRepository.save(address);

            return addressList;
        }
    }
}

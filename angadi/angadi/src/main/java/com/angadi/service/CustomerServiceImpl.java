package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.model.Customer;
import com.angadi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) throws CustomerException {

        Optional<Customer> optional = Optional.ofNullable(customerRepository.findByEmail(customer.getEmail()));

        if (optional.isPresent()) {
            Customer c = optional.get();
            return customerRepository.save(customer);
        }
        throw new CustomerException("No customer found with given Email -> " + customer.getEmail());
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
        List<Customer> customerList = customerRepository.findAll();
        return customerList;
    }
}

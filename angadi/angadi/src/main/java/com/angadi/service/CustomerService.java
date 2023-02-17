package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.model.Address;
import com.angadi.model.Customer;

import java.util.List;
import java.util.Set;

public interface CustomerService {

    public Customer saveCustomer(Customer customer);

    /*customer specific method*/
    public Customer updateCustomer(Customer customer) throws CustomerException;

    /*admin specific method*/
    public Customer deleteCustomer(String email) throws CustomerException;

    /*customer specific method*/
    public Customer getCustomerDetails() throws CustomerException;

    /*admin specific method*/
    public List<Customer> getAllCustomerDetails() throws CustomerException;
}

package com.angadi.service;

import com.angadi.exception.CustomerException;
import com.angadi.model.Customer;

import java.util.List;

public interface CustomerService {

    public Customer saveCustomer(Customer customer);

    /*customer specific method*/
    public Customer updateCustomer(Customer customer) throws CustomerException;

    /*admin specific method*/
    public Customer deleteCustomer(String email) throws CustomerException;

    /*customer specific method*/
    public Customer getCustomerDetails(String email) throws CustomerException;

    /*admin specific method*/
    public List<Customer> getAllCustmerDetails() throws CustomerException;
}

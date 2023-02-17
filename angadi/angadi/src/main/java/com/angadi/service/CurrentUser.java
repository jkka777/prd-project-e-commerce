package com.angadi.service;

import com.angadi.model.Customer;
import com.angadi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    @Autowired
    private CustomerRepository customerRepository;

    /* To get the current user who was logged in, we pull the authentication object from security context and
       and search through db, then return the customer who was logged in.
    */

    public Customer getLoggedInCustomer() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        return customerRepository.findByEmail(authentication.getName());
    }
}
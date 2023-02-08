package com.angadi.controller;

import com.angadi.model.Customer;
import com.angadi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/logIn")
    public ResponseEntity<Customer> getCurrentLogInCustomerHandler(Authentication authentication) {

        Customer customer = customerRepository.findByEmail(authentication.getName());

        if (customer == null) {
            throw new BadCredentialsException("Invalid Username or password!");
        }
        return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
    }
}

package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.model.Address;
import com.angadi.model.Customer;
import com.angadi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity<>("welcome", HttpStatus.OK);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome to spring boot";
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> saveCustomerHandler(@Valid @RequestBody Customer customer) {

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.OK);
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<Customer> updateCustomerHandler(@Valid @RequestBody Customer customer) throws CustomerException {
        return new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteCustomer/{email}")
    public ResponseEntity<Customer> deleteCustomerHandler(@Valid @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(customerService.deleteCustomer(email), HttpStatus.OK);
    }

    @GetMapping("/getCustomerDetails")
    public ResponseEntity<Customer> getCustomerHandler() throws CustomerException {
        return new ResponseEntity<>(customerService.getCustomerDetails(), HttpStatus.OK);
    }

    /* admin specific functionality */
    @GetMapping("/getAllCustomerDetails/")
    public ResponseEntity<List<Customer>> getAllCustomersHandler() throws CustomerException {
        return new ResponseEntity<>(customerService.getAllCustomerDetails(), HttpStatus.CREATED);
    }

}

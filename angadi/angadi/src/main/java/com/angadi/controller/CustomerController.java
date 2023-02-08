package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.model.Address;
import com.angadi.model.Customer;
import com.angadi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> saveCustomerHandler(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }

    @PostMapping("/addCustomerAddress/{email}")
    public ResponseEntity<Set<Address>> saveCustomerAddressHandler(@Valid @RequestBody Address address, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(customerService.addAddress(address, email), HttpStatus.CREATED);
    }

    @PutMapping("/updateCustomer/{email}")
    public ResponseEntity<Customer> updateCustomerHandler(@Valid @RequestBody Customer customer, String email) throws CustomerException {
        return new ResponseEntity<>(customerService.updateCustomer(customer, email), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCustomer/{email}")
    public ResponseEntity<Customer> deleteCustomerHandler(@Valid String email) throws CustomerException {
        return new ResponseEntity<>(customerService.deleteCustomer(email), HttpStatus.CREATED);
    }

    @GetMapping("/getCustomerDetails/{email}")
    public ResponseEntity<Customer> getCustomerHandler(@Valid String email) throws CustomerException {
        return new ResponseEntity<>(customerService.getCustomerDetails(email), HttpStatus.CREATED);
    }

    @GetMapping("/getAllCustomerDetails/")
    public ResponseEntity<List<Customer>> getAllCustomersHandler() throws CustomerException {
        return new ResponseEntity<>(customerService.getAllCustomerDetails(), HttpStatus.CREATED);
    }

}

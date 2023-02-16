package com.angadi.controller;

import com.angadi.exception.AddressException;
import com.angadi.exception.CustomerException;
import com.angadi.model.Address;
import com.angadi.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/addAddress/{email}")
    public ResponseEntity<Set<Address>> saveAddressHandler(@Valid @RequestBody Address address, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(addressService.addAddress(address, email), HttpStatus.CREATED);
    }

    @PutMapping("/updateAddress/{email}")
    public ResponseEntity<Address> updateAddressHandler(@Valid @RequestBody Address address, @PathVariable String email) throws CustomerException, AddressException {
        return new ResponseEntity<>(addressService.updateAddress(address, email), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteAddress/{email}/{addressId}")
    public ResponseEntity<Address> deleteAddressHandler(@Valid @PathVariable Integer addressId, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(addressService.deleteAddress(addressId, email), HttpStatus.CREATED);
    }

    @GetMapping("/getAddressList/{email}")
    public ResponseEntity<Set<Address>> getAllAddressHandler(@Valid @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(addressService.getAllAddressOfaUser(email), HttpStatus.CREATED);
    }

}

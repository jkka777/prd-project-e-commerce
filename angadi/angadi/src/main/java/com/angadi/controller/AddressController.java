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

    @PostMapping("/addAddress")
    public ResponseEntity<Set<Address>> saveAddressHandler(@Valid @RequestBody Address address) throws CustomerException {
        return new ResponseEntity<>(addressService.addAddress(address), HttpStatus.CREATED);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<Address> updateAddressHandler(@Valid @RequestBody Address address) throws CustomerException, AddressException {
        return new ResponseEntity<>(addressService.updateAddress(address), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAddress/{addressId}")
    public ResponseEntity<Address> deleteAddressHandler(@Valid @PathVariable Integer addressId) throws CustomerException {
        return new ResponseEntity<>(addressService.deleteAddress(addressId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getUserAddressList")
    public ResponseEntity<Set<Address>> getUserAddressListHandler() throws CustomerException {
        return new ResponseEntity<>(addressService.getAllAddressOfaUser(), HttpStatus.OK);
    }

    /* admin specific functionality */
    @GetMapping("/getAllAddressList")
    public ResponseEntity<Set<Address>> getAllUserAddressListHandler() throws CustomerException {
        return new ResponseEntity<>(addressService.getAllAddress(), HttpStatus.OK);
    }

}

package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.exception.ShippingException;
import com.angadi.model.Shipping;
import com.angadi.service.ShippingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping("/addShippingDetails/{email}")
    public ResponseEntity<Shipping> addShippingHandler(@Valid @RequestBody Shipping shipping, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(shippingService.addShippingDetails(shipping, email), HttpStatus.CREATED);
    }

    @PutMapping("/updateShippingDetails/{email}")
    public ResponseEntity<Shipping> updateShippingHandler(@Valid @RequestBody Shipping shipping, @PathVariable String email) throws CustomerException, ShippingException {
        return new ResponseEntity<>(shippingService.updateShippingDetails(shipping, email), HttpStatus.OK);
    }

    @DeleteMapping("/deleteShippingDetails/{email}/{shippingId}")
    public ResponseEntity<Shipping> cancelShippingHandler(@Valid @PathVariable Integer shippingId, @PathVariable String email) throws CustomerException, ShippingException {
        return new ResponseEntity<>(shippingService.cancelShippingDetails(shippingId, email), HttpStatus.OK);
    }
}

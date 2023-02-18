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

    @PostMapping("/addShippingDetails")
    public ResponseEntity<Shipping> addShippingHandler(@Valid @RequestBody Shipping shipping) throws CustomerException {
        return new ResponseEntity<>(shippingService.addShippingDetails(shipping), HttpStatus.CREATED);
    }

    @PutMapping("/updateShippingDetails")
    public ResponseEntity<Shipping> updateShippingHandler(@Valid @RequestBody Shipping shipping) throws CustomerException, ShippingException {
        return new ResponseEntity<>(shippingService.updateShippingDetails(shipping), HttpStatus.OK);
    }

    @DeleteMapping("/deleteShippingDetails/{shippingId}")
    public ResponseEntity<Shipping> cancelShippingHandler(@Valid @PathVariable Integer shippingId) throws CustomerException, ShippingException {
        return new ResponseEntity<>(shippingService.cancelShippingDetails(shippingId), HttpStatus.OK);
    }
}

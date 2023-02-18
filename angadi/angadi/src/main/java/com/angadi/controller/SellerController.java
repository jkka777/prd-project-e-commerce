package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.exception.SellerException;
import com.angadi.model.Seller;
import com.angadi.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PostMapping("/addSeller")
    public ResponseEntity<Seller> saveSellerHandler(@Valid @RequestBody Seller seller) throws CustomerException {
        return new ResponseEntity<>(sellerService.addSellerDetails(seller), HttpStatus.CREATED);
    }

    @PutMapping("/updateSeller")
    public ResponseEntity<Seller> updateSellerHandler(@Valid @RequestBody Seller seller) throws CustomerException, SellerException {
        return new ResponseEntity<>(sellerService.updateSellerDetails(seller), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteSeller")
    public ResponseEntity<Seller> deleteSellerHandler(@Valid @RequestBody Seller seller) throws CustomerException, SellerException {
        return new ResponseEntity<>(sellerService.addSellerDetails(seller), HttpStatus.OK);
    }

    @GetMapping("/getAllSellersList")
    public ResponseEntity<List<Seller>> getSellersList() {
        return new ResponseEntity<>(sellerService.getAllSellerDetails(), HttpStatus.OK);
    }
}

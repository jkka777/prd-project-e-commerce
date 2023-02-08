package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.exception.SupplierException;
import com.angadi.model.Suppliers;
import com.angadi.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/addSupplier/{email}")
    public ResponseEntity<Suppliers> saveSupplierHandler(@Valid @RequestBody Suppliers suppliers, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(supplierService.addSupplier(suppliers, email), HttpStatus.CREATED);
    }

    @PutMapping("/updateSupplier/{email}")
    public ResponseEntity<Suppliers> updateSupplierHandler(@Valid @RequestBody Suppliers suppliers, @PathVariable String email) throws CustomerException, SupplierException {
        return new ResponseEntity<>(supplierService.updateSupplier(suppliers, email), HttpStatus.OK);
    }

    @DeleteMapping("/deleteSupplier/{email}")
    public ResponseEntity<Suppliers> deleteSupplierHandler(@Valid @RequestBody Suppliers suppliers, @PathVariable String email) throws CustomerException, SupplierException {
        return new ResponseEntity<>(supplierService.addSupplier(suppliers, email), HttpStatus.OK);
    }

    @GetMapping("/getAllSuppliers")
    public ResponseEntity<List<Suppliers>> saveSupplierHandler() {
        return new ResponseEntity<>(supplierService.getAllSupplierDetails(), HttpStatus.OK);
    }
}

package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.model.Payments;
import com.angadi.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    @PostMapping("/addPayment/{email}")
    public ResponseEntity<Payments> makePaymentHandler(@RequestBody Payments payments, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(paymentsService.addPaymentToOrder(payments, email), HttpStatus.CREATED);
    }
}

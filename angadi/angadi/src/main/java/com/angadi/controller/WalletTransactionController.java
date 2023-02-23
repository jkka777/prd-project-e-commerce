package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.exception.WalletException;
import com.angadi.exception.WalletTransactionException;
import com.angadi.model.WalletTransactions;
import com.angadi.service.WalletTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/walletTransaction")
public class WalletTransactionController {

    @Autowired
    private WalletTransactionService walletTransactionService;

    @PostMapping("/addWalletTransaction")
    public ResponseEntity<WalletTransactions> saveWalletTransactionHandler(@Valid @RequestBody WalletTransactions walletTransactions) throws CustomerException, WalletException {
        return new ResponseEntity<>(walletTransactionService.addTransaction(walletTransactions), HttpStatus.CREATED);
    }

    @PutMapping("/makePayment")
    public ResponseEntity<WalletTransactions> makePaymentToOrder(@Valid @RequestBody WalletTransactions walletTransactions, @RequestParam Integer orderId) throws CustomerException, WalletException, OrderException {
        return new ResponseEntity<>(walletTransactionService.makePaymentToOrder(walletTransactions, orderId), HttpStatus.OK);
    }

    @GetMapping("/viewTransactions")
    public ResponseEntity<Set<WalletTransactions>> viewWalletTransactionHandler() throws CustomerException, WalletTransactionException {
        return new ResponseEntity<>(walletTransactionService.viewTransaction(), HttpStatus.OK);
    }

    /* admin specific functionality */
    @GetMapping("/viewTransactions/{email}")
    public ResponseEntity<Set<WalletTransactions>> viewAllWalletTransactionHandler(@Valid @PathVariable String email) throws CustomerException, WalletTransactionException {
        return new ResponseEntity<>(walletTransactionService.viewAllTransactions(email), HttpStatus.OK);
    }
}

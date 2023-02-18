package com.angadi.controller;

import com.angadi.exception.CustomerException;
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

    @PostMapping("/addWalletTransaction/{email}")
    public ResponseEntity<WalletTransactions> saveWalletTransactionHandler(@Valid @RequestBody WalletTransactions walletTransactions, @PathVariable String email) throws CustomerException, WalletException {
        return new ResponseEntity<>(walletTransactionService.addTransaction(walletTransactions, email), HttpStatus.CREATED);
    }

    @GetMapping("/viewTransactions")
    public ResponseEntity<Set<WalletTransactions>> viewWalletTransactionHandler() throws CustomerException, WalletTransactionException {
        return new ResponseEntity<>(walletTransactionService.viewTransaction(), HttpStatus.CREATED);
    }

    @GetMapping("/viewTransactions/{email}")
    public ResponseEntity<Set<WalletTransactions>> viewAllWalletTransactionHandler(@Valid @PathVariable String email) throws CustomerException, WalletTransactionException {
        return new ResponseEntity<>(walletTransactionService.viewAllTransactions(email), HttpStatus.CREATED);
    }
}

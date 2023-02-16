package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.exception.WalletException;
import com.angadi.model.Customer;
import com.angadi.model.Wallet;
import com.angadi.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/addWallet/{email}")
    public ResponseEntity<Wallet> addWalletHandler(@Valid @RequestBody Wallet wallet, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(walletService.addWallet(wallet, email), HttpStatus.CREATED);
    }

    @PutMapping("/addBalance/{email}/{amount}")
    public ResponseEntity<Wallet> addBalanceToWalletHandler(@Valid @PathVariable Integer amount, @PathVariable String email) throws CustomerException, WalletException {
        return new ResponseEntity<>(walletService.addBalanceToWallet(amount, email), HttpStatus.OK);
    }

    @GetMapping("/getBalance/{email}/{walletId}")
    public ResponseEntity<Integer> showBalanceHandler(@Valid @PathVariable Integer walletId, @PathVariable String email) throws CustomerException, WalletException {
        return new ResponseEntity<>(walletService.showBalance(walletId, email), HttpStatus.OK);
    }

    @PutMapping("/fundTransfer/{email}/{walletId}/{amount}/{description}")
    public ResponseEntity<Wallet> fundTransferHandler(@Valid @PathVariable Integer walletId, @PathVariable Integer amount, @PathVariable String description, @PathVariable String email) throws CustomerException, WalletException {
        return new ResponseEntity<>(walletService.transferAmount(walletId, description, amount, email), HttpStatus.OK);
    }
}

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

    @PostMapping("/addWallet")
    public ResponseEntity<Wallet> addWalletHandler(@Valid @RequestBody Wallet wallet) throws CustomerException {
        return new ResponseEntity<>(walletService.addWallet(wallet), HttpStatus.CREATED);
    }

    @PutMapping("/addBalance/{amount}")
    public ResponseEntity<Wallet> addBalanceToWalletHandler(@Valid @PathVariable Integer amount) throws CustomerException, WalletException {
        return new ResponseEntity<>(walletService.addBalanceToWallet(amount), HttpStatus.OK);
    }

    @GetMapping("/getBalance")
    public ResponseEntity<Integer> showBalanceHandler() throws CustomerException, WalletException {
        return new ResponseEntity<>(walletService.showBalance(), HttpStatus.OK);
    }

    @PutMapping("/fundTransfer/{walletId}/{amount}/{description}")
    public ResponseEntity<Wallet> fundTransferHandler(@Valid @PathVariable Integer walletId, @PathVariable Integer amount, @PathVariable String description) throws CustomerException, WalletException {
        return new ResponseEntity<>(walletService.transferAmount(walletId, description, amount), HttpStatus.OK);
    }
}

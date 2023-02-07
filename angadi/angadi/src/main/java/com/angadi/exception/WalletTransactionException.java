package com.angadi.exception;

public class WalletTransactionException extends RuntimeException {
    public WalletTransactionException() {
    }

    public WalletTransactionException(String message) {
        super(message);
    }
}

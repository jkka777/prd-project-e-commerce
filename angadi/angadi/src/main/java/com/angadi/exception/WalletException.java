package com.angadi.exception;

public class WalletException extends RuntimeException {
    public WalletException() {
    }

    public WalletException(String message) {
        super(message);
    }
}

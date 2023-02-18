package com.angadi.exception;

public class CartItemException extends RuntimeException {
    public CartItemException() {
    }

    public CartItemException(String message) {
        super(message);
    }
}

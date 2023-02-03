package com.angadi.exception;

public class ShippingException extends RuntimeException {
    public ShippingException() {
    }

    public ShippingException(String message) {
        super(message);
    }
}

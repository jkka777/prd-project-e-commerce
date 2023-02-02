package com.angadi.exception;

public class OrderDetailsException extends RuntimeException {
    public OrderDetailsException() {
    }

    public OrderDetailsException(String message) {
        super(message);
    }
}

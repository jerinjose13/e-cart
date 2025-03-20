package com.hoiii.e_Cart.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {

        super(message);
    }
}

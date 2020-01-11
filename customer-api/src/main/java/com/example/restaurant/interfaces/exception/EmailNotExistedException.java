package com.example.restaurant.interfaces.exception;

public class EmailNotExistedException extends RuntimeException {

    public EmailNotExistedException(String email) {
        super("Email is not existed : "+email);
    }
}

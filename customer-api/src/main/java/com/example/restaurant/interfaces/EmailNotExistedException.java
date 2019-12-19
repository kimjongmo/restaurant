package com.example.restaurant.interfaces;

public class EmailNotExistedException extends RuntimeException {

    public EmailNotExistedException(String email) {
        super("Email is not existed : "+email);
    }
}

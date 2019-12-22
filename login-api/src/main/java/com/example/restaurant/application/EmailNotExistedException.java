package com.example.restaurant.application;

public class EmailNotExistedException extends RuntimeException {

    public EmailNotExistedException(String email) {
        super("Email is not existed : "+email);
    }
}

package com.example.restaurant.interfaces.exception;

public class TokenNotExistedException extends RuntimeException {

    public TokenNotExistedException(){
        super("토큰이 존재하지 않습니다.");
    }
}

package com.example.restaurant.interfaces;

import com.example.restaurant.application.EmailNotExistedException;
import com.example.restaurant.application.PasswordWrongException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionErrorAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {PasswordWrongException.class, EmailNotExistedException.class})
    public String sessionGetFail(RuntimeException ex){
        return ex.getMessage();
    }


}

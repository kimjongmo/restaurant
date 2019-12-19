package com.example.restaurant.interfaces;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SessionRequestDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}

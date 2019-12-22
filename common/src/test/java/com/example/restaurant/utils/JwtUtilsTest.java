package com.example.restaurant.utils;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class JwtUtilsTest {

    private String secret = "12345678901234567890123456789012";
    private JwtUtils jwtUtils;

    @Before
    public void setUp() {
        jwtUtils = new JwtUtils(secret);
    }

    @Test
    public void createToken() {
        String token = jwtUtils.createToken(1004L, "tester",null);
        assertThat(token, containsString("."));
    }

    @Test
    public void getClaims() {
        Long userId = 1004L;
        String name = "tester";

        String token = jwtUtils.createToken(userId,name,null);
        Claims claims = jwtUtils.getClaims(token);

        assertThat(claims.get("name"),is(name));
        assertThat(claims.get("userId",Long.class),is(userId));
    }
}
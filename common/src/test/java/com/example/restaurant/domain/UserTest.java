package com.example.restaurant.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void create() {
        User user = User.builder()
                .email("test1@example.com")
                .name("test")
                .level(1L)
                .build();

        assertThat(user.getName(),is("test"));
        assertThat(user.getLevel(),is(1L));
        assertThat(user.isAdmin(),is(false));
        assertThat(user.getEmail(),is("test1@example.com"));
        assertThat(user.isActive(),is(true));

        user.deactivate();

        assertThat(user.isActive(),is(false));
    }
}
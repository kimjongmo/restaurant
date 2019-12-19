package com.example.restaurant.application;

import com.example.restaurant.interfaces.EmailExistedException;
import com.example.restaurant.domain.User;
import com.example.restaurant.domain.UserRepository;
import com.example.restaurant.interfaces.EmailNotExistedException;
import com.example.restaurant.interfaces.PasswordWrongException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository,bCryptPasswordEncoder);
    }

    @Test
    public void registerUser(){
        String name = "tester";
        String email = "tester@example.com";
        String password = "test";
        User user = userService.registerUser(name,email,password);
        verify(userRepository).save(any());
    }


    @Test(expected = EmailExistedException.class)
    public void registerUserWithExistedEmail(){
        String name = "tester";
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .id(1005L)
                .name(name)
                .email(email)
                .password(password)
                .level(1L)
                .build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        userService.registerUser(email,name,password);

        verify(userRepository).findByEmail(any());
        verify(userRepository,never()).save(any());
    }

    @Test
    public void authenticateWithValidData(){
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder().email(email).password(userService.encodedPassword(password)).build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        User user = userService.authenticate(email,"test");

        assertThat(user.getEmail(),is(email));
    }


    @Test(expected = EmailNotExistedException.class)
    public void authenticateWithNotExistedEmail(){
        String email = "tester@example.com";
        String password = "test";

        given(userRepository.findByEmail(email)).willThrow(new EmailNotExistedException(email));

        userService.authenticate(email,password);
        verify(userRepository).findByEmail(email);
    }

    @Test(expected = PasswordWrongException.class)
    public void authenticateWithNotMatchPassword(){
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder().email(email).password(userService.encodedPassword(password)).build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        userService.authenticate(email,"test1");

    }
}